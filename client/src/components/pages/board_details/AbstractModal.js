import React, { Component } from 'react'
import PropTypes from 'prop-types'

import {
    Button,
    Modal,
    Form,
    Icon,
    Transition,
    Header,
    Input,
    Message
} from 'semantic-ui-react'

import { withUtilsConsumer } from '../../withUtilsConsumer'

/**
 * This component handles the state required to display a modal with the configurations given in props.
 * It also manages the logic for making the HTTP request, because that information is obtained from a HAL+Foms document.
 */
class AbstractModal extends Component {
    static propTypes = {
        /**
         * The custom link relation. Its value is an URI identifying a HAL+Forms resource
         */
        rel: PropTypes.string.isRequired,
        /**
         * The URL to be used on an HTTP request that is sent when the form is submitted.
         * An example of a value is the href property in a item object of the collection+json media type.
         */
        relativeUrl: PropTypes.string.isRequired,
        /**
         * Descriptor of the function: (Response) -> Promise<object>
         * {@link https://developer.mozilla.org/pt-BR/docs/Web/API/Response}
         */
        asyncParseRspToModel: PropTypes.func.isRequired,
        /**
         * Current resource model representation on the UI.
         */
        currentResource: PropTypes.object.isRequired,
        /**
         * Contains the necessary information about each property present on a HAL+Forms document.
         * For simplicity "HLD" will refer to "HAL+Forms document".
         * The format of this object is: 
         *  - property names of this object are equal to the name property of a HLD;
         *  - property value is equal to an object that can specify the 'type', being a known HTML5 input type,
         * and it can specify a 'render' function, that gets passed the 'property' object (present in the HLD), and the
         * 'onChange' function.
         * The onChange function has the descriptor: (SyntheticEvent, string) -> void. The first parameter being the event object,
         * and the second being the propertyName. There must be a wrapper HTML element with a data-value attribute.
         * This render function gives the outside component, control over the UI of a field.
         */
        propertiesConfig: PropTypes.object.isRequired,
        updateUi: PropTypes.func.isRequired,
        cancelActionText: PropTypes.string,
        submitActionText: PropTypes.string,
        errorHeader: PropTypes.string,
        errorContent: PropTypes.string,
        submitActionText: PropTypes.string,
        submitActionText: PropTypes.string,
    }

    state = {
        reqInfo: null, // The result from HAL+Forms response
        formSuccess: false,
        formError: null
    }

    async componentDidMount() {
        const { utilsObj, rel } = this.props
        const rsp = await utilsObj.asyncRelativeFetch(rel)
        const { _templates: { default: reqInfo } } = await utilsObj.asyncParseHalFormRsp(rsp)

        this.setState({ reqInfo })
    }

    onChangeField = e => {
        const property = this.state.reqInfo.properties.find(p => p.name === e.target.name)
        property.value = e.target.value
    }

    buildTextField = (label, inputName) => (
        <>
            <label>{label}</label>
            <Input 
                name={inputName}
                onChange={this.onChangeField}
            />  
        </>
    )

    /**
     * This function requires that the current target (e.currentTarget) element to have the attribute data-value.
     */
    onChangeCheckBox = (e, propertyName) => {
        e.preventDefault()
        const property = this.state.reqInfo.properties.find(p => p.name === propertyName)
        // Using currentTarget because it refers the div element instead of target that refers the label.        
        property.value = e.currentTarget.dataset.value
    }

    onCloseModal = e => {
        if (this.updatedResource)
            this.props.updateUi(this.updatedResource)

        // The next time the modal opens, a previous success message doesn't appear
        this.setState({ formSuccess: false })
    }

    // An arrow function is used because this function is used in an onClick prop, meaning there 
    // is no need to use Function::bind() to capture "this".
    submitOnClickHandler = async e => {
        this.setState({ formSuccess: false })

        const { reqInfo } = this.state
        const { relativeUrl, asyncParseRspToModel } = this.props
        this.checkRequiredProperties(reqInfo)

        try {
            const rsp = await this.props.utilsObj.asyncHalFormsRequest(reqInfo, relativeUrl)
            if (!rsp.ok) throw rsp // The response may have error information (problem+json media type)

            this.updatedResource = await asyncParseRspToModel(rsp)
            this.setState({ formSuccess: true })
        } catch(error) {
            if (error.json) {
                const formError = await error.json()
                this.setState({ formError })
            } else
                this.setState({ formError: new Error('An error occured') })
        }
    }

    /**
     * If the user didn't provide a value for a required property, then fill 
     * with previous value that is present on the currentResource prop of this component.
     * @param {object} reqInfo - information about the request to an endpoint of the Web API
     */
    checkRequiredProperties(reqInfo) {
        const { currentResource } = this.props
        reqInfo.properties.map(p => {
            if (p.required && (!p.value || p.value === ''))
                p.value = currentResource[p.name]
            
            return p
        })
    }

    render = () =>
        this.state.reqInfo
            ?  
                <AbstractModalPresenter
                    reqInfo={this.state.reqInfo}
                    submitOnClickHandler={this.submitOnClickHandler}
                    onChangeCheckBox={this.onChangeCheckBox}
                    onCloseModal={this.onCloseModal}
                    buildTextField={this.buildTextField}
                    propertiesConfig={this.props.propertiesConfig}
                    formSuccess={this.state.formSuccess}
                    formError={this.state.formError}
                    modalHeader={this.props.modalHeader}
                    cancelActionText={this.props.cancelActionText}
                    submitActionText={this.props.submitActionText}
                    errorHeader={this.props.errorHeader}
                    errorContent={this.props.errorContent}
                    successHeader={this.props.successHeader}
                    successContent={this.props.successContent}
                    triggerIcon={this.props.triggerIcon}
                />
            : null
}

function AbstractModalPresenter(props) {
    const {
        reqInfo,
        submitOnClickHandler,
        onChangeCheckBox,
        onCloseModal,
        buildTextField,
        propertiesConfig,
        formSuccess,
        formError,
        modalHeader,
    } = props

    let {
        cancelActionText,
        submitActionText,
        errorHeader,
        errorContent,
        successHeader,
        successContent,
        triggerIcon,
    } = props

    cancelActionText = cancelActionText || 'Cancel'
    submitActionText = submitActionText || 'Submit'
    errorHeader      = errorHeader  || 'An error occured'
    errorContent     = errorContent || null
    successHeader    = successHeader  || 'Operation was successful'
    successContent   = successContent || 'You can exit this modal now'
    triggerIcon      = triggerIcon || 'setting'

    function propertyToJsx(property) {
        const { name, prompt } = property
        let fieldChildren

        if (!propertiesConfig[name])
            throw new Error(`Error: A configuration for the property: ${name} wasn't provided!`)

        switch (propertiesConfig[name].type) {
            case 'text': 
                fieldChildren = buildTextField(prompt, name)
                break
            case 'checkbox':
                fieldChildren = propertiesConfig[name].render(property, onChangeCheckBox)
        }
        
        return (
            <Form.Field>
                {fieldChildren}
            </Form.Field>
        )
    }

    return (
        <Modal
            onClose={onCloseModal}
            closeIcon
            trigger={
                <Button
                    color='grey'
                    icon={triggerIcon}
                    floated='right'
                    style={{ marginTop: -35 }} 
                />
            }
        >
            <Header content={modalHeader} />
            <Modal.Content>
                <Form success={formSuccess} error={formError}>
                    { reqInfo.properties.map(propertyToJsx) }
                    <Transition.Group animation='drop' duration={500}>
                        { formSuccess &&
                            <Message
                                success
                                header={successHeader}
                                content={successContent} />
                        }
                    </Transition.Group>
                    
                    <Message
                        error
                        header={errorHeader}
                        content={errorContent}
                    />
                </Form>
            </Modal.Content>
            <Modal.Actions>
                <Button color='red'>
                    <Icon name='remove' /> {cancelActionText}
                </Button>
                <Button color='green' onClick={submitOnClickHandler}>
                    <Icon name='checkmark' /> {submitActionText}
                </Button>
            </Modal.Actions>
        </Modal>
    )
}

export default withUtilsConsumer(AbstractModal)
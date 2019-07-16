import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Button, Modal, Form, Icon, Header, Input, Message } from 'semantic-ui-react'

import { rels } from '../../common/rels-registery'

import rspToBoardAsync from '../../api/mapper/board-mapper'
import { withUtilsConsumer } from '../../components/withUtilsConsumer'

/**
 * This component handles the state required to update/edit a board and the logic for making that HTTP request.
 */
class EditBoardButton extends Component {
    static propTypes = {
        board: PropTypes.object
    }

    state = {
        reqInfo: null, // The result from HAL+Forms response
        name: null,
        description: null,
        formSuccess: false
    }

    async componentDidMount() {
        //TODO: use props and context API
        const { utilsObj } = this.props
        const rsp = await utilsObj.asyncRelativeFetch(rels.editBoard)
        const { _templates: { default: reqInfo } } = await utilsObj.asyncParseHalFormRsp(rsp)

        this.setState({ reqInfo })
    }

    onChangeField = e => {
        const property = this.state.reqInfo.properties.find(p => p.name === e.target.name)
        property.value = e.target.value
    }

    onCloseModal = e => {
        if (this.newBoard)
            this.props.updateBoard(this.newBoard)

        // The next time the modal opens, a previous success message doesn't appear
        this.setState({ formSuccess: false })
    }

    // An arrow function is used because this function is used in an onClick prop, meaning there 
    // is no need to use Function::bind() to capture "this".
    submitEditBlackboardHandler = async e => {
        const { reqInfo } = this.state
        this.checkRequiredProperties(reqInfo)
        
        try {
            const rsp = await this.props.utilsObj.asyncHalFormsRequest(reqInfo, this.props.board.editBoardHref)
            if (!rsp.ok) throw rsp // The response may have error information (problem+json media type)

            this.newBoard = await rspToBoardAsync(rsp)
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
     * with previous value that is present on the board prop of this component.
     * @param {object} reqInfo - information about the request to an endpoint of the Web API
     */
    checkRequiredProperties(reqInfo) {
        const { board } = this.props
        reqInfo.properties.map(property => {
            if (property.required && (!property.value || property.value === ''))
                property.value = board[property.name]
            return property
        })
    }

    render = () =>
        this.state.reqInfo
            ?  
                <EditBoardButtonPresenter
                    reqInfo={this.state.reqInfo}
                    formSuccess={this.state.formSuccess}
                    formError={this.state.formError}
                    onChangeField={this.onChangeField}
                    onChangeCheckBox={this.onChangeCheckBox}
                    submitOnClickReq={this.submitEditBlackboardHandler}
                    onCloseModal={this.onCloseModal}
                />
            : null
}

function EditBoardButtonPresenter(props) {
    const {
        reqInfo,
        onChangeField,
        submitOnClickReq,
        formSuccess,
        formError,
        onCloseModal
    } = props

    function propertyToJsx({ name, required, prompt }) {
        let fieldChildren
        if (name === 'name')
            fieldChildren = buildTextField(prompt, 'name', onChangeField)
        else if (name === 'description')
            fieldChildren = buildTextField(prompt, 'description', onChangeField)
        
        return (
            <Form.Field required={required}>
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
                    icon='pencil alternate'
                    floated='right'
                    style={{ marginTop: -35 }} 
                />
            }
        >
            <Header content={'Edit the Board\'s information'} />
            <Modal.Content>
                <Form success={formSuccess} error={formError}>
                    { reqInfo.properties.map(propertyToJsx) }
                    <Message
                        success
                        header='Update Board Completed'
                        content='You can exit this modal now' />
                    <Message
                        error
                        header={(formError && formError.title) || 'An error occured'}
                        content={(formError && formError.detail) || ''}
                    />
                </Form>
            </Modal.Content>
            <Modal.Actions>
                <Button color='red'>
                    <Icon name='remove' /> Cancel
                </Button>
                <Button color='green' onClick={submitOnClickReq}>
                    <Icon name='checkmark' /> Submit
                </Button>
            </Modal.Actions>
        </Modal>
    )
}

const buildTextField = (label, inputName, onChange) => (
    <>
        <label>{label}</label>
        <Input 
            name={inputName}
            onChange={onChange}
        />  
    </>
)

export default withUtilsConsumer(EditBoardButton)
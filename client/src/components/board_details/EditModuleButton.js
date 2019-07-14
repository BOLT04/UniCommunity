import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Button, Modal, Form, Checkbox, Icon, Header, Input, Message } from 'semantic-ui-react'

import Blackboard from './model/Blackboard'
import { rels } from '../../common/rels-registery'
import asyncParseHalFormRsp from '../../api/mapper/halForms-mapper'
import { asyncRelativeFetch } from '../../common/common'
import { asyncHalFormsRequest } from '../../common/common'

import { parseOutputModelToBlackboard } from '../../api/mapper/board-mapper'

/**
 * This component handles the state required to update/edit a module (e.g. Blackboard) and the logic for
 * making that HTTP request.
 */
export default class EditModuleButton extends Component {
    static propTypes = {
        blackboard: PropTypes.instanceOf(Blackboard),
        board: PropTypes.object, // Used as an argument for the function parseOutputModelToBlackboard
    }

    state = {
        reqInfo: null, // The result from HAL+Forms response
        name: null,
        description: null,
        notificationLevel: null,
        formSuccess: false
    }

    async componentDidMount() {
        //TODO: use props and context API
        //const rsp = await this.props.asyncRelativeFetch(rels.editBlackboard)
        const rsp = await asyncRelativeFetch(rels.editBlackboard)
        const { _templates: { default: reqInfo } } = await asyncParseHalFormRsp(rsp)

        this.setState({ reqInfo })
    }

    /**
     * Example values: e.target.name = 'name'; e.target.value = 'TPC'
     */
    onChangeField = e => {
        //this.setState({ [e.target.name]: e.target.value })
        const property = this.state.reqInfo.properties.find(p => p.name === e.target.name)
        property.value = e.target.value
    }

    onChangeCheckBox = (e, propertyName) => {
        e.preventDefault()
        const property = this.state.reqInfo.properties.find(p => p.name === propertyName)
        // Using currentTarget because it refers the div element instead of target that refers the label.        
        property.value = e.currentTarget.dataset.level
    }

    onCloseModal = e => this.props.updateBlackboard(this.newBlackboard)

    // An arrow function is used because this function is used in an onClick prop, meaning there 
    // is no need to use Function::bind() to capture "this".
    submitEditBlackboardHandler = async e => {
        const { reqInfo } = this.state
        try {
            const rsp = await asyncHalFormsRequest(reqInfo, this.props.blackboard.editLinkHref)
            debugger
            if (!rsp.ok) throw rsp // The response may have error information (problem+json media type)

            const body = await rsp.json()
            this.newBlackboard = parseOutputModelToBlackboard(this.props.board, body)
            this.setState({ formSuccess: true })
        } catch(error) {
            debugger
            if (error.json) {
                const formError = await error.json()
                this.setState({ formError })
            } else
                this.setState({ formError: new Error('An error occured') })
        }
    }

    render = () =>
        this.state.reqInfo
            ?  
                <EditModuleButtonPresenter
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

function EditModuleButtonPresenter(props) {
    const {
        reqInfo,
        onChangeField,
        onChangeCheckBox,
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
        else if (name === 'notificationLevel') {
            // It is easier to give the property name as an argument of the function: onChangeCheckBox,
            // instead of repeating the same prop on the Checkbox react component with the name as the value.
            const onChange = e => onChangeCheckBox(e, name)

            fieldChildren = (
                <>
                    <label>{prompt}</label>

                    <Checkbox 
                        data-level='1'
                        label='never receive notifications'
                        onChange={onChange} />
                    <Checkbox
                        data-level='2'
                        label='always receive notifications'
                        onChange={onChange} />
                    <Checkbox
                        data-level='3'
                        label='only receive notifications with IMPORTANT'
                        onChange={onChange} />
                </>
            )
        }
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
                    icon='setting'
                    floated='right'
                    style={{ marginTop: -35 }} 
                />
            }
        >
            <Header content='Configure the Blackboard settings' />
            <Modal.Content>
                <Form success={formSuccess} error={formError}>
                    { reqInfo.properties.map(propertyToJsx) }
                    <Message
                        success
                        header='Update Blackboard Settings Completed'
                        content='You can exit this modal now' />
                    <Message
                        error
                        header={formError && formError.title || 'An error occured'}
                        content={formError && formError.detail || ''}
                    />
                    {/*
                    <Form.Field>
                        <label>Name</label>
                        <Input 
                            name='name'
                            onChange={onChangeField}
                        />   
                    </Form.Field>
                    <Form.Field>
                        <label>Description</label>
                        <Input 
                            name='description'
                            onChange={onChangeField}
                        />   
                    </Form.Field>
                    <Form.Field>
                        <label>Notification Level</label>

                        <Checkbox 
                            data-level='1'
                            label='never receive notifications'
                            onChange={onChangeCheckBox} />
                        <Checkbox
                            data-level='2'
                            label='always receive notifications'
                            onChange={onChangeCheckBox} />
                        <Checkbox
                            data-level='3'
                            label='only receive notifications with IMPORTANT'
                            onChange={onChangeCheckBox} />
                    </Form.Field>
                    */}
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
import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Button, Modal, Form, Icon, Header, Input, Message } from 'semantic-ui-react'

import { rels } from '../../common/rels-registery'
import asyncParseHalFormRsp from '../../api/mapper/halForms-mapper'
import { asyncHalFormsRequest } from '../../common/common'

import rspToBoardAsync from '../../api/mapper/board-mapper'

/**
 * This component handles the state required to update/edit a board and the logic for making that HTTP request.
 */
export default class EditBoardButton extends Component {
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
        const rsp = await this.props.asyncRelativeFetch(rels.editBoard)
        const { _templates: { default: reqInfo } } = await asyncParseHalFormRsp(rsp)

        this.setState({ reqInfo })
    }

    onChangeField = e => {
        const property = this.state.reqInfo.properties.find(p => p.name === e.target.name)
        property.value = e.target.value
    }

    onCloseModal = e => this.props.updateBoard(this.newBoard)

    // An arrow function is used because this function is used in an onClick prop, meaning there 
    // is no need to use Function::bind() to capture "this".
    submitEditBlackboardHandler = async e => {
        const { reqInfo } = this.state
        try {
            const rsp = await asyncHalFormsRequest(reqInfo, this.props.board.editLinkHref)
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
                        header={formError && formError.title || 'An error occured'}
                        content={formError && formError.detail || ''}
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
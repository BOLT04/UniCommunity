import React, { Component } from 'react'

import { Button, Form, Checkbox } from 'semantic-ui-react'

import auth from '../../../service/auth'

import asyncRspToComment from '../../../service/mapper/comment-mapper'

export default class CreateComment extends Component {
    commentText = ''
    isAnonymous = false

    state = {
        hasText: false
    }

    submitCreateCommentReq = async (e) => {
        const rsp = await this.props.api.createCommentAsync(
            this.props.serverUrl,
            this.commentText,
            this.isAnonymous
        )
        const comment = await asyncRspToComment(rsp)
    
        this.props.onCreateCommentHandler(e, comment)
    }

    commentTxtAreaHandler = e => {
        if (e.target.value.length > 0)
            this.setState({hasText: true})

        this.commentText = e.target.value
    }

    onChangeCheckBox = (e, { checked }) => {
        e.preventDefault()
        this.isAnonymous = checked
    }

    render() {
        const disabled = !this.state.hasText
        const style = disabled ? {cursor: 'not-allowed'} : {}
        const user = localStorage.getObject('user')

        return (
            <>
                {auth.isAuthenticated() &&
                    <>
                        <p>Comment as {user.name}</p>
                        <Form reply>
                            <Form.TextArea onChange={this.commentTxtAreaHandler}/>
                            
                            <Form.Field>
                                <Checkbox label='Send Anonimously' onChange={this.onChangeCheckBox} />
                            </Form.Field>

                            <Button 
                                content='Comment' 
                                labelPosition='left' 
                                icon='edit'
                                disabled={disabled}
                                style={style}
                                primary
                                onClick={this.submitCreateCommentReq}
                            />
                        </Form>
                    </>
                }
            </>
        )
    }
}
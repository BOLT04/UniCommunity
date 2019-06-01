import React, { Component } from 'react'

import { Button, Form, Checkbox } from 'semantic-ui-react'

import auth from '../auth'

import rspToForumItemAsync from '../../api/mapper/forumItem-mapper'
import { asyncFetch } from '../../api/ForumApiImpl'

export default class CreateComment extends Component {
    commentText = ''
    isAnonymous = false// TODO: use this on the response

    state = {
        hasText: false
    }

    submitCreateCommentReq = async () => {
        console.log(this.commentText)
    
        // TODO: is there a way to easily document that this component receives this.props.location.state.serverHref
        //todo: from the Link component used in NavBar.js available in React router?
        const rsp = await this.props.api.createCommentAsync(
            this.props.serverUrl,
            this.commentText,
            this.isAnonymous
        )
        console.log(rsp)
        //TODO:const comment = await rspToCommentAsync(rsp)
    
        
        // Display the comment on the same view
        //TODO: this
    }

    //TODO: test this setState behaviour
    commentTxtAreaHandler = e => {
        if (e.target.value.length > 0)
            this.setState({hasText: true})
        //else

        this.commentText = e.target.value
    }

    onChangeCheckBox = (e, { checked }) => {
        e.preventDefault()
        this.isAnonymous = checked
    }

    render() {
        const disabled = !this.state.hasText
        const style = disabled ? {cursor: 'not-allowed'} : {}
        console.log('oi ' + auth.isAuthenticated())
        const user = localStorage.getItem('user')

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
import React, { Component } from 'react'

import { Button, Form } from 'semantic-ui-react'

import auth from '../auth'

import rspToForumItemAsync from '../../api/mapper/forumItem-mapper'
import { asyncFetch } from '../../api/ForumApiImpl'

export default class CreateComment extends Component {
    commentText = ''
    state = {
        hasText: false
    }

    async componentDidMount() {
        const rsp = await asyncFetch(this.props.location.state.getPostUrl)
        const post = await rspToForumItemAsync(rsp)
        console.log(post)
        
        this.setState({ post, loading: false })
    }

    submitCreateCommentReq = async () => {
        console.log(this.commentText)
    
        // TODO: is there a way to easily document that this component receives this.props.location.state.serverHref
        //todo: from the Link component used in NavBar.js available in React router?
        const rsp = await this.props.api.createCommentAsync(this.props.serverUrl, this.commentText)
        console.log(rsp)
        const comment = await rspToCommentAsync(rsp)
    
        
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

    render() {
        const disabled = this.state.hasText
        const style = disabled ? {cursor: 'not-allowed'} : {}
        
        return (
            <>
                {auth.isAuthenticated() &&
                    <>
                        <p>Comment as {auth.user.username}</p>
                        <Form reply>
                            <Form.TextArea onChange={this.commentTxtAreaHandler}/>
                            
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
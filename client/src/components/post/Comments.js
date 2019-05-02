import React, { Component } from 'react'

import { Button, Form, Header } from 'semantic-ui-react'

import { Comment } from './Comment'
import auth from '../auth'

import asyncCollectionRspToList from '../../api/mapper/collectionJson-mapper'
import { asyncFetch } from '../../api/ForumApiImpl'

export default class Comments extends Component {

    state = { }

    async componentDidMount() {
        const rsp = await asyncFetch(this.props.location.state.getPostCommentsUrl)
        const comments = await asyncCollectionRspToList(rsp)
        console.log(comments)

        this.setState({ comments })
    }

    render() {
        return (
            <>
                <Comment.Group>
                    <Header as='h3' dividing>
                        Comments option2
                    </Header>
                    
                    {this.state.comments.map(c => <Comment comment={c} />)}
                </Comment.Group>
            </>
        )
    }
}
import React, { Component } from 'react'

import { Header } from 'semantic-ui-react'

import { Comment } from './Comment'

import asyncCollectionRspToList from '../../api/mapper/collectionJson-mapper'
import { asyncFetch } from '../../api/ForumApiImpl'

export default class Comments extends Component {

    constructor(props) {
        super(props)

        this.state = { }
        if (props.comments) // In case the parent component already has fetched the comments.
            this.state.comments = props.comments
    }

    async componentDidMount() {
        if (this.props.comments) return

        const rsp = await asyncFetch(this.props.serverUrl)
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
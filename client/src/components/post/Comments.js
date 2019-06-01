import React, { Component } from 'react'

import { Header, Comment as CommentSUI } from 'semantic-ui-react'

import { Comment } from './Comment'
import ListLoader from '../ListLoader'

import asyncCollectionRspToList from '../../api/mapper/collectionJson-mapper'

import { COLLECTION_JSON } from '../../common/constants'

export default class Comments extends Component {

    constructor(props) {
        super(props)

        this.state = { 
            commenst: props.comments || [] // In case the parent component already has fetched the comments.
        }
    }

    async componentDidMount() {
        //if (this.props.comments) return

        const rsp = await this.props.asyncRelativeFetch(this.props.serverUrl, COLLECTION_JSON)
        const comments = await asyncCollectionRspToList(rsp)

        this.setState({ comments })
    }

    renderComments = () =>
        this.state.comments.map(c => <Comment comment={c} />)

    render() {
        debugger
        return (
            <>
                <CommentSUI.Group>
                    <Header as='h3' dividing>
                        Comments
                    </Header>
                    
                    <ListLoader
                        list={this.state.comments}
                        emptyListHeaderMsg='No Coments available'
                        emptyListMsg='Be the first to comment this post.'
                        render={this.renderComments}
                    />                 
                </CommentSUI.Group>
            </>
        )
    }
}
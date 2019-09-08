import React, { Component } from 'react'

import { Header, Comment as CommentSUI } from 'semantic-ui-react'

import { Comment } from './Comment'
import ListLoader from '../../common/ListLoader'

import asyncCollectionRspToList from '../../../service/mapper/collectionJson-mapper'

import { COLLECTION_JSON } from '../../../common/constants'
import { withUtilsConsumer } from '../../withUtilsConsumer'

class Comments extends Component {

    constructor(props) {
        super(props)

        this.state = { 
            comments: props.comments || [] // In case the parent component already has fetched the comments.
        }
    }

    async componentDidMount() {
        if (this.props.comments) return

        const rsp = await this.props.utilsObj.asyncRelativeFetch(this.props.serverUrl, COLLECTION_JSON)
        if (!rsp.ok) throw rsp
        
        const comments = (await asyncCollectionRspToList(rsp)).items
        this.setState({ comments })
    }

    static getDerivedStateFromProps(nextProps, prevState){
        const { comments } = nextProps

        if ( comments && (comments !== prevState.comments) && (comments.length >= 1)) {
            prevState.comments.push(comments[comments.length -1])
            return { comments: prevState.comments}
        }
        else return null
    }

    renderComments = () =>
        this.state.comments.map(c => <Comment comment={c} />)

    render() {
        return (
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
        )
    }
}

export default withUtilsConsumer(Comments)
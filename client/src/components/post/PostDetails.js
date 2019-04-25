'use strict'
import React, { Component } from 'react'
import { Link } from "react-router-dom"

import { Icon, Button, Loader, Segment } from 'semantic-ui-react'


import Header from '../Header'
import ForumPostHeader from '../ForumPostHeader'

import rspToForumItemAsync from '../../api/mapper/forumItem-mapper'

import { getForumPostAsync } from '../../api/ForumApiImpl'

export default class PostDetails extends Component {

    state = {
        loading: true
    }

    async componentDidMount() {
        const rsp = await getForumPostAsync(this.props.location.state.getPostUrl)
        const post = await rspToForumItemAsync(rsp)
        console.log(post)
        
        this.setState({ post, loading: false })
    }

    render() {
        const renderPost = () => (
            <Segment>
                <ForumPostHeader post={post} />
                <Header 
                    className='ui blue header'
                    header={post.name}
                    content={post.content}
                    inMd
                />
            </Segment>
        )

        const { post, loading } = this.state
        console.log(this.props)
        console.log(post)
        return (
            <>
                { post === undefined 
                    ? <Loader active={loading} inline />
                    : renderPost()              
                }
            </>
        )
    }
}
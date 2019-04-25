'use strict'
import React, { Component } from 'react'
import { Link } from "react-router-dom"

import { Icon, Button, Loader } from 'semantic-ui-react'
import Header from '../Header'
import ForumPostHeader from '../ForumPostHeader'

import { getForumPostAsync } from '../../api/ForumApiImpl'

export default class PostDetails extends Component {

    state = {
        loading: true
    }

    async componentDidMount() {
        const rsp = await getForumPostAsync(this.props.location.state.getPostUrl)
        const rspObj = await rsp.json()
        console.log(rspObj)
        
        //this.setState({ post })
    }

    render() {
        const renderPost = () => (
            <>
                <ForumPostHeader post={post} />
                <Header 
                    className='ui blue header'
                    header={post.name}
                    content={post.content}
                />
            </>
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
                
                    <Button primary icon floated='right' labelPosition='right' style={{ marginTop: -35 }} >
                        <Icon name='plus' />
                        Create new
                    </Button>
            </>
        )
    }
}
import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Accordion, Icon, Transition, List } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

/**
 * This component is used to display the modules/blackboards of a Board, for example the Summary and
 * Announcements.
 */
export default class Forum extends Component {
    
    state = {
        posts: []
    }

    async componentDidMount() {
        //const rsp = await this.props.api.getForumPostsAsync()
        //const posts = await rspToForumPostsAsync(rsp)
        this.setState({
            posts: [
                {
                    title: "Anuncio 1",
                    smallDesc: "Exemplo de um anuncio....",
                    author: "Paulo Pereira",
                    createdAt: "16-04-2019"
                },
                {
                    title: "Anuncio 2",
                    smallDesc: "Exemplo de um anuncio2....",
                    author: "Pedro Pereira",
                    createdAt: "16-04-2019"
                },
                {
                    title: "Anuncio 2",
                    smallDesc: "Exemplo de um anuncio2....",
                    author: "Pedro Pereira",
                    createdAt: "16-04-2019"
                    //authorHref
                }
            ]
        })
    }

    render() {
//TODO: clean this up later
        const posts = this.props.posts || this.state.posts
console.log(posts)
        return (
            <List divided link verticalAlign='middle'>
                {posts.map(post => <ForumItem post={post} key={post.href}/>)}      
            </List>
        )
    }
}

function ForumItem({ post }) {
    
    function buildWithoutDesc() {
        return ( 
            <>
                <List.Header as='a'>
                    {post.author !== undefined &&
                        <div>
                            Published by <strong>{post.author}</strong> at {post.createdAt.toLocaleString()}
                        </div>
                    } 
                    {' '}
                    {post.title}
                </List.Header>
                <List.Description>
                    <ReactMarkdown source={post.smallDesc} />
                </List.Description>
            </>
        )
    }
    function decideContent() {
        return (
            <List.Content style={{marginTop: 10}}>
                {post.smallDesc !== undefined
                    ? buildWithoutDesc() 
                    : <List.Header as='a'>{post.name}</List.Header>
                }    
            </List.Content>
        )
    }

    return (
        <List.Item>
            { decideContent() }    
        </List.Item>
    )
}
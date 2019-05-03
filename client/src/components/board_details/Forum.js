'use strict'
import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'

import { Accordion, Icon, Transition, List } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

import routes from '../../common/routes'

/**
 * This component is used to display the Forum module of a Board.
 */
export default class Forum extends Component {
    static propTypes = {
        board: PropTypes.object
    }

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

        return (
            <List divided link verticalAlign='middle'>
                { posts.map(post => 
                    <ForumItem board={this.props.board} post={post} key={post.href} />)
                }      
            </List>
        )
    }
}

function ForumItem({ post, board }) {
    const boardId = board.id

    function decideContent() {
        return (
            <List.Content style={{marginTop: 10}}>
                {post.smallDesc !== undefined
                    ? buildWithoutDesc() 
                    : 
                        <Link to={{
                            pathname: routes.getPostDetailsUri(boardId, post.id),
                            state: {getPostUrl: post.href}
                        }}>
                            <List.Header as='a'>{post.name}</List.Header>
                        </Link>
                }    
            </List.Content>
        )
    }

    function buildWithoutDesc() {
        return ( 
            <Link to={{
                pathname: routes.getPostDetailsUri(boardId, post.id),
                state: {getPostUrl: post.href}
            }}>
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
            </Link>
        )
    }

    return (
        <List.Item>
            { decideContent() }    
        </List.Item>
    )
}
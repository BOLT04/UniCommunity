import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'

import { List } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

import routes from '../../../common/routes'

/**
 * This component is used to display the Forum module of a Board.
 */
export default class Forum extends Component {
    static propTypes = {
        board: PropTypes.object
    }

    render() {
        const posts = this.props.posts || []

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

    const decideContent = () => (
        <List.Content style={{marginTop: 10}}>
            {post.content
                ? buildWithDesc() 
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
    

    const buildWithDesc = () => ( 
        <Link to={{
            pathname: routes.getPostDetailsUri(boardId, post.id),
            state: {getPostUrl: post.href}
        }}>
            <List.Header as='a'>
                {post.authorName &&
                    <div>
                        Published by <strong>{post.authorName}</strong> at {post.createdAt.toLocaleString()}
                    </div>
                } 
                {' '+ post.name}
            </List.Header>
            <List.Description>
                <ReactMarkdown source={post.content} />
            </List.Description>
        </Link>
    )
    

    return (
        <List.Item>
            { decideContent() }    
        </List.Item>
    )
}
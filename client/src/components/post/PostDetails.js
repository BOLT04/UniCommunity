import React, { Component } from 'react'
import { Link } from 'react-router-dom'

// Semantic UI imports
import { 
    Icon,
    Loader, 
    Segment, 
    Divider, 
    Header as SemanticHeader 
} from 'semantic-ui-react'

// custom component imports
import Header from '../Header'
import ForumPostHeader from '../ForumPostHeader'
import Comments from './Comments'
import CreateComment from './CreateComment'

// api imports
import rspToForumItemAsync from '../../api/mapper/forumItem-mapper'

import { APPLICATION_HAL_JSON } from '../../common/constants'

export default class PostDetails extends Component {

    state = {
        loading: true
    }

    async componentDidMount() {
        const rsp = await this.props.asyncRelativeFetch(this.props.location.state.getPostUrl, APPLICATION_HAL_JSON)
        const post = await rspToForumItemAsync(rsp)
        console.log(post)
debugger
        this.setState({ post, loading: false })
    }

    onCreateCommentHandler = (e, comment) => {
        this.setState(state => {
          state.post.comments.push(comment)
     debugger
          return {
            error: undefined,// TODO: god damn it...we should probably handle errors later...if creating comment fails, for example 500 bc the server went down cause it couldnt handle a lot of boys commenting at the same time...concurrency boy!! its life
            post: state.post
          }
        })
    }

    render() {
        const { post, loading } = this.state
debugger
        //TODO: if this gets too big consider making a functional component for it, receiving 'post obj' as a prop
        const renderPost = () => (
            <Segment>
                <ForumPostHeader post={post} />
                <Header
                    className='ui blue header'
                    header={post.name}
                    content={post.content}
                    inMd
                />

                <br />
                <CreateComment 
                    serverUrl={post.links.createComment}
                    api={this.props.api}
                    onCreateCommentHandler={this.onCreateCommentHandler}
                />

                <Divider horizontal>
                    <SemanticHeader as='h4'>
                        <Icon name='comments' />
                        Comments
                    </SemanticHeader>
                </Divider>
                <Comments 
                    comments={post.comments}
                    serverUrl={post.links.getComments}
                    asyncRelativeFetch={this.props.asyncRelativeFetch}
                />
            </Segment>
        )

        console.log(this.props)
        console.log(post)
        return !post
                    ? <Loader active={loading} inline />
                    : renderPost()
    }
} 
import React, { Component } from 'react'

// Semantic UI imports
import { 
    Icon,
    Loader, 
    Segment, 
    Divider, 
    Header as SemanticHeader 
} from 'semantic-ui-react'

// custom component imports
import Header from '../../Header'
import ForumPostHeader from './ForumPostHeader'
import Comments from './Comments'
import CreateComment from './CreateComment'

// api imports
import rspToForumItemAsync from '../../../service/mapper/forumItem-mapper'

import { APPLICATION_HAL_JSON } from '../../../common/constants'
import { withUtilsConsumer } from '../../withUtilsConsumer'

class PostDetails extends Component {

    state = {
        loading: true
    }

    async componentDidMount() {
        //try {
            const rsp = await this.props.utilsObj.asyncRelativeFetch(this.props.location.state.getPostUrl, APPLICATION_HAL_JSON)
            const post = await rspToForumItemAsync(rsp)
            
            this.setState({ post, loading: false })
        //} catch (e) {
            //this.props.handleError(e)
        //}
    }

    onCreateCommentHandler = (e, comment) => {
        this.setState(state => {
          state.post.comments.push(comment)

          return {
            error: undefined,
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
                <ForumPostHeader authorName={post.authorName} createdAt={post.createdAt} />
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

        return !post
                    ? <Loader active={loading} inline />
                    : renderPost()
    }
}

export default withUtilsConsumer(PostDetails)
//export default withErrorHandler(withUtilsConsumer(PostDetails))
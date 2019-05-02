import React, { Component } from 'react'
import { Link } from 'react-router-dom'

// Semantic UI imports
import { 
    Icon, 
    Button, 
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
import { asyncFetch } from '../../api/ForumApiImpl'

export default class PostDetails extends Component {

    state = {
        loading: true
    }

    async componentDidMount() {
        const rsp = await asyncFetch(this.props.location.state.getPostUrl)
        const post = await rspToForumItemAsync(rsp)
        console.log(post)

        this.setState({ post, loading: false })
    }

    render() {
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
                <CreateComment />

                <Divider horizontal>
                    <SemanticHeader as='h4'>
                        <Icon name='comments' />
                        Comments
                    </SemanticHeader>
                </Divider>
                <Comments />
            </Segment>
        )

        const { post, loading } = this.state
        console.log(this.props)
        console.log(post)
        return (
            <>
                {post === undefined
                    ? <Loader active={loading} inline />
                    : renderPost()
                }
            </>
        )
    }
} 
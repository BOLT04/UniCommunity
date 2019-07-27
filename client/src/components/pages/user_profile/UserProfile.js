import React, { Component } from 'react'
import { Link } from 'react-router-dom'

// Semantic UI imports
import { 
    Icon,
    Image,
    Loader, 
    Segment, 
    Divider,
    Grid,
    Header as SemanticHeader 
} from 'semantic-ui-react'

// custom component imports
import Header from './Header'
import ForumPostHeader from './ForumPostHeader'
import Comments from './post/Comments'
import CreateComment from './post/CreateComment'

// api imports
import rspToForumItemAsync from '../api/mapper/forumItem-mapper'

import { APPLICATION_HAL_JSON } from '../common/constants'

export default class UserProfile extends Component {

    state = {
        loading: true
    }

    async componentDidMount() {
        /*const rsp = await this.props.asyncRelativeFetch(this.props.location.state.getPostUrl, APPLICATION_HAL_JSON)
        const post = await rspToForumItemAsync(rsp)
        console.log(post)
debugger
        this.setState({ post, loading: false })
        */
    }


    render() {
        const { post, loading } = this.state
        return (
            <>
                <Image
                    src='/img/default-profile.png'
                    size='medium'
                    floated='left'
                    circular />

                 <SemanticHeader as='h2'>Jose dabe</SemanticHeader>
                <Grid>
                    <Grid.Row>
                        <h3>Bio:</h3><br/><p>yoyoyoy</p>
                    </Grid.Row>
                    <Grid.Row>
                        <h3>GitHub ID:</h3> <p>yoyoyoy</p>
                    </Grid.Row>
                    <Grid.Row>
                        <h3>Email:</h3>yoyoyoy@a.com
                    </Grid.Row>
                    <Grid.Row>
                        <h3>Roles:</h3>Professor
                    </Grid.Row>
                </Grid>
            </>
        )
    }
} 
import React, { Component } from 'react'

// Semantic UI imports
import {
    Image,
    Grid,
    Header
} from 'semantic-ui-react'

export default class UserProfile extends Component {

    state = {
        loading: true
    }

    render() {
        const { body: user } = this.props.location.state

        return (
            <>
                <Image
                    src='/img/default-profile.png'
                    size='medium'
                    floated='left'
                    circular />

                <Header as='h2'>{user.name}</Header>
                <Grid>
                    <Grid.Row>
                        <strong>GitHub ID:</strong>{user.githubId}
                    </Grid.Row>
                    <Grid.Row>
                        <strong>Email:</strong>{user.email}
                    </Grid.Row>
                    <Grid.Row>
                        <strong>Role:</strong>{user.role}
                    </Grid.Row>
                </Grid>
            </>
        )
    }
} 
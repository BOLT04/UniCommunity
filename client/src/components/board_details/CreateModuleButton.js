'use strict'
import React, { Component } from 'react'
import { Link } from "react-router-dom"

import { Icon, Button } from 'semantic-ui-react'

export default class CreateModuleButton extends Component {
    render() {
        return (
            <Link to={this.props.linkToObj}>
                <Button primary icon floated='right' labelPosition='right' style={{ marginTop: -35 }} >
                    <Icon name='plus' />
                    Create new
                </Button>
            </Link>
        )
    }
}
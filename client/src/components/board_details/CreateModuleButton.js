'use strict'
import React from 'react'
import { Link } from "react-router-dom"

import { Icon, Button } from 'semantic-ui-react'

export const CreateModuleButton = ({ linkToObj }) => (
    <Link to={linkToObj}>
        <Button primary icon floated='right' labelPosition='right' style={{ marginTop: -35 }} >
            <Icon name='plus' />
            Create new
        </Button>
    </Link>
)
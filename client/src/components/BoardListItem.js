'use strict'
import React from 'react'

import { List } from 'semantic-ui-react'

import routes from '../../common/routes'

/**
 * This React functional component Describes a single board item in a list of boards.
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     header: string      -> This is the text of the header.
 *     description: string -> This is the description text of the board.
 * } 
 */
export const BoardListItem = ({ board: { id, header, description } }) => (
    <List.Item>
        <List.Content>
            <Link to={{
                pathname: routes.getBoardUri(id),
                state: { board }
            }}>
                <List.Header as='a'>{header}</List.Header>
                <List.Description>
                    {description}
                </List.Description>
            </Link>
        </List.Content>
    </List.Item>
)

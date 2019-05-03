import React from 'react'
import { Link } from 'react-router-dom'

import { List } from 'semantic-ui-react'

import routes from '../common/routes'

/**
 * This React functional component Describes a single board item in a list of boards.
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     id: int             -> This is the board identifier. 
 *     header: string      -> This is the text of the header.
 *     description: string -> This is the description text of the board.
 * } 
 */
export const BoardListItem = ({ board }) => (
    <List.Item>
        <List.Content>
            <Link to={{
                pathname: routes.getBoardUri(board.id),
                state: { board }
            }}>
                <List.Header as='a'>{board.header}</List.Header>
                <List.Description>
                    {board.description}
                </List.Description>
            </Link>
        </List.Content>
    </List.Item>
)

import React from 'react'
import { Link } from 'react-router-dom'

import { List } from 'semantic-ui-react'

import { removeFunctionsFrom } from '../common/common'
import routes from '../common/routes'

/**
 * This React functional component Describes a single board item in a list of boards.
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     id: int             -> This is the board identifier. 
 *     name: string        -> This is the name of the board.
 *     description: string -> This is the description text of the board.
 * } 
 */
export const BoardListItem = ({ board }) => (
    <List.Item>
        <List.Content>
            <Link to={{
                pathname: routes.getBoardUri(board.id),
                state: { board: removeFunctionsFrom(board) }
            }}>
                <List.Header as='a'>{board.name}</List.Header>
                <List.Description>
                    {board.description}
                </List.Description>
            </Link>
        </List.Content>
    </List.Item>
)

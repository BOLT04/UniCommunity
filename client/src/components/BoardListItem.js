import React from 'react'
import { Link } from 'react-router-dom'

import { List } from 'semantic-ui-react'

import { removeFunctionsFrom } from '../common/common'
import routes from '../common/routes'
import { rels } from '../common/rels-registery'

import SubscribeModal from './SubscribeModal'
/**
 * This React functional component Describes a single board item in a list of boards.
 * The board property is used to send state to another component using React-Router, meaning
 * it can't have functions as properties.
 * @see {@link https://developer.mozilla.org/pt-BR/docs/Web/API/History_API#O_método_pushState()}.
 * @param {object} props - The properties of this component. It must include a property board with the following:
 * {
 *     id: int             -> This is the board identifier. 
 *     name: string        -> This is the name of the board.
 *     description: string -> This is the description text of the board.
 * }
 */
export const BoardListItem = ({ board }) => (
    <List.Item>
        <List.Content>
            <List.Header as='a'>{board.name}</List.Header>
            <List.Description>
                {board.description}
            </List.Description>
        </List.Content>
        { board.hasLinkRel(rels.addMemberToBoard) &&
            <List.Content floated='right'>
                <SubscribeModal board={board} />
            </List.Content>
        }
    </List.Item>
)

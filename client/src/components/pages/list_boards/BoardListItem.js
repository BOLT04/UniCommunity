import React, { Component } from 'react'
import { Link } from 'react-router-dom'

import { List } from 'semantic-ui-react'

import { removeFunctionsFrom } from '../../../common/common'
import routes from '../../../common/routes'
import { rels } from '../../../common/rels-registery'

import SubscribeModal from './SubscribeModal'
import UnsubscribeModal from './UnsubscribeModal'
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
export class BoardListItem extends Component {

    decideContent() {
        const { board } = this.props

        const hasSubscribeLink = board.hasLinkRel(rels.addMemberToBoard)
        const hasUnsubscribeLink = board.hasLinkRel(rels.removeMemberToBoard)
        //TODO: uncomment this when the server only provides 1 link (subscribe or unsubscribe)
        return this.renderWithSubButton(board)
        //hasSubscribeLink && !hasUnsubscribeLink
            //? 
            //: this.renderWithUnsubButton(board)
    }

    renderWithSubButton(board) {
        return (
            <>
                <List.Content>
                    <List.Header as='a'>{board.name}</List.Header>
                    <List.Description>
                        {board.description}
                    </List.Description>
                </List.Content>

                <List.Content floated='right'>
                    <SubscribeModal board={board} />
                </List.Content>
            </>
        )
    }

    renderWithUnsubButton(board) {
        return (
            <>
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

                <List.Content floated='right'>
                    <UnsubscribeModal board={board} />
                </List.Content>
            </>
        )
    }

    render = () => (
        <List.Item>
            { this.decideContent() }
        </List.Item>
    )
}

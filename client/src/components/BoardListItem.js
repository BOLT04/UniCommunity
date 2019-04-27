'use strict'
import React from 'react'

import { List } from 'semantic-ui-react'

/**
 * This React functional component Describes a single board item in a list of boards.
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     header: string    -> This is the text of the header.
 *     description: string   -> This is the text to display with the content.
 * } 
 */
export default function BoardListItem({ board: { header, description } }) {
    return (
        <List.Item>
            <List.Content>
                <List.Header as='a'>{header}</List.Header>
                <List.Description>
                    {description}
                </List.Description>
            </List.Content>
        </List.Item>
    )
}

import React, { Component } from 'react'

import { Editor } from 'slate-react'
import { Value } from 'slate'

// Create our initial value.
const initialValue = Value.fromJSON({
    document: {
        nodes: [
            {
                object: 'block',
                type: 'paragraph',
                nodes: [
                    {
                        object: 'text',
                        leaves: [
                            {
                                text: 'A line of text in a paragraph.',
                            },
                        ],
                    },
                ],
            },
        ],
    },
})

export default class CreatePost extends Component {
    // Set the initial value when the app is first constructed.
    state = {
        value: initialValue
    }

    // On change, update the app's React state with the new editor value.
    onChange = ({ value }) => {
        this.setState({ value })
    }

    render = () => (
        <Editor value={this.state.value} onChange={this.onChange} />
    )
}

/*
import React, { Component } from 'react'

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
        
    )
}
*/


'use strict';
import React from 'react'
import ReactDOM from 'react-dom'
import MdEditor from 'react-markdown-editor-lite'
 
const mock_content = "Hello.\n\n * This is markdown.\n * It is fun\n * Love it or leave it."
export default class CreatePost extends React.Component {
  handleEditorChange ({html, md}) {    
    console.log('handleEditorChange', html, md)
  }
  render() {
    return (      
      <div style={{height: 500}}>
        <MdEditor
          value={mock_content}
          onChange={this.handleEditorChange} 
        />                
      </div>
    )
  }
}
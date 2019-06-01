import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Input, Form, Button } from 'semantic-ui-react'

import MdEditor from 'react-markdown-editor-lite'
 
import { createForumPostsAsync } from '../../api/ForumApiImpl'
import rspToBoardAsync from '../../api/mapper/board-mapper'

import routes from '../../common/routes'
import CreateBoardApi from '../../api/CreateBoardApi';

/**
 * This component contains a Form UI with all the common fields of any Module Item (Blackboard 
 * item or Forum item) like title and content. The rest of the fields that are specific to each Module is provided
 * as input in a property called children of props.
 * Note: The children will be rendered inside a Form component from semantic-ui-react module
 * @see {@link https://react.semantic-ui.com/collections/form/|Form}, so they should use
 * Form.Field in most cases.
 */
export default class CreateModuleItem extends Component {
  titleVal = ''
  mdEditor = null

  handleEditorChange({ html, text }) {    
    //console.log('handleEditorChange', html, text)
  }

  onTitleChange = e => {
    e.preventDefault()
    this.titleVal = e.target.value
  }

  /**
   * Call parent function that handles the 'submit' action, passing it the stored user input.
   */
  submitOnClickReq = e => {
    e.preventDefault()
    this.props.submitOnClick(this.titleVal, this.mdEditor.getMdValue())
  }

  render() {
    const { header } = this.props

    return (      
      <div style={{height: 500}}>
        <h1 className='ui header'>{header}</h1>
        <Form>
          <Form.Field required>
            <label>Title</label>
            <Input 
              name='title'
              onChange={this.onTitleChange}
            />   
          </Form.Field>
          <Form.Field required>
            <MdEditor
              value=''
              ref={node => this.mdEditor = node}
              onChange={this.handleEditorChange} 
            />
          </Form.Field>

          {this.props.children}   
        </Form>

        <Button content='Post' primary style={{marginTop: 10}} onClick={this.submitOnClickReq} />
      </div>
    )
  }
}

'use strict'
import React, { Component } from 'react'

import { Input, Form, TextArea, Button } from 'semantic-ui-react'

import MdEditor from 'react-markdown-editor-lite'
 
import { createForumPostsAsync } from '../../api/ForumApi'
import rspToBoardAsync from '../../api/mapper/board-mapper'

export default class CreatePost extends Component {
  //TODO: should these props be in the object "state" of react?? and use this.setState()????
  titleVal = ""
  mdEditor = null

  handleEditorChange({html, text}) {    
    console.log('handleEditorChange', html, text)
  }

  onTitleChange = e => {
    this.titleVal = e.target.value
  }

  // An arrow function is used because this function is used in an onClick prop, meaning there 
  // is no need to use Function::bind() to capture "this".
  submitCreatePostReq = async () => {
    console.log(this.titleVal)
    console.log(this.mdEditor.getMdValue())

    const rsp = await createForumPostsAsync('', this.titleVal, this.mdEditor.getMdValue())

    //TODO: move this code that handles hal+json and responses from the API to another place,
    //todo: in this class its only logic related to this component
    
    //TODO: the idea behind looking at these links is to know if we can navigate/redirect to the board view
    //todo: if the server says we can't (doesnt include the link on the response) then we cant.
    if (rsp._links) {
      const boardUrl = rsp._links['up']
      if (boardUrl) {
        const rsp = await this.props.api.getBoardAsync(boardUrl)
        const board = await rspToBoardAsync(rsp)
        this.props.history.push(`/board`, { board })
      }
    }
  }

  render() {
    return (      
      <div style={{height: 500}}>
        <h1 className="ui header">Create a Post</h1>
        <Form>
          <Form.Field>
            <label>Title</label>
            <Input 
              name="title"
              onChange={this.onTitleChange}
            />   
          </Form.Field>
          <Form.Field>
            <MdEditor
              ref={node => this.mdEditor = node}
              onChange={this.handleEditorChange} 
            />
          </Form.Field>
        </Form>

        <Button content='Post' primary style={{marginTop: 10}} onClick={this.submitCreatePostReq}/>
      </div>
    )
  }
}

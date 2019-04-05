'use strict'
import React, { Component } from 'react'

import { Input, Form, TextArea, Button } from 'semantic-ui-react'

import MdEditor from 'react-markdown-editor-lite'
 
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
  submitCreatePostReq = () => {
    console.log(this.titleVal)
    console.log(this.mdEditor.getMdValue())
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

import React, { Component } from 'react'
import { Input, Form, TextArea, Button } from 'semantic-ui-react'

import BoardTemplate from './BoardTemplate'

import rspToBoardAsync from '../api/mapper/board-mapper'

export default class CreateBoard extends Component {
  //TODO: should these props be in the object "state" of react?? and use this.setState()????
  titleVal = ""
  descVal = ""
  templates = [] //array<string>

  onTitleChange = e => {
    this.titleVal = e.target.value
  }

  onDescriptionChange = e => {
    this.descVal = e.target.value
  }

  submitCreateBoardReq = async () => {
    console.log(this.descVal)
    console.log(this.titleVal)

    this.boardTemplate.updatePropsTemplates()
    console.log(this.templates)

    debugger
    console.log(this.props.api)
    this.props.api.createBoardAsync()
      .then(rsp => {
        debugger
        console.log(rsp)
      })

    const rsp = await this.props.api.createBoardAsync()
    console.log(rsp)
    const board = await rspToBoardAsync(rsp)
    console.log(board)
    this.props.history.push(`/board`, {board})
  }

//TODO: is getting the ref of BoardTemplate and do: this.boardTemplate.updateTemplates() the best solution??

  render() {// TODO: Adjacent JSX elements must be wrapped  in an enclosing tag??? Why must there be a div?
    return (
      <div>
        <h1 className="ui header">Create a board</h1>
        <Form>
          <Form.Field>
            <label>Title</label>
            <Input 
              name="title"
              onChange={this.onTitleChange}
            />   
          </Form.Field>
          <Form.Field>
            <label>Description</label>
            <TextArea onChange={this.onDescriptionChange} placeholder='Give more details about this board (optional)' />
          </Form.Field>
        </Form>
        
        <BoardTemplate 
          ref={boardTemplate => this.boardTemplate = boardTemplate} 
          templates={this.templates}/>

        <Button content='Create' primary style={{marginTop: 10}} onClick={this.submitCreateBoardReq}/>
      </div>
    )
  }
}

import React, { Component } from 'react'
import { Input, Form, TextArea, Button } from 'semantic-ui-react'

import BoardTemplate from './BoardTemplate'

import rspToBoardAsync from '../api/mapper/board-mapper'

export default class CreateBoard extends Component {
  titleVal = ""
  descVal = ""
  modules = [] //array<string>
  templateId = null

  onTitleChange = e => {
    this.titleVal = e.target.value
  }

  onDescriptionChange = e => {
    this.descVal = e.target.value
  }

  // TODO: I think I'm now realizing (when trying to make tests for this component) that this class might
  //include too much logic, for example to test the logic in submitCreateBoardReq that is a event handler...
  //i can't really know if the function was called... Perhaps a wrapper around this component should be made
  //that knows how to handle api requests, and this component is just concerned with visual rendering!
  //Meaning that this component receives the submitBtnHandler through props.
  
  /**
   * Makes a request to the API using the object received on props called api, to create a board.
   */
  submitCreateBoardReq = async () => {
    console.log(this.descVal)
    console.log(this.titleVal)

    this.boardTemplate.updatePropsTemplates()
    console.log(this.modules)
    console.log(this.templateId)

    // Validate user input
    // In case the user chooses neither options
    if (this.templateId == null && this.modules.length == 0)
      throw Error('please specify the modules manually or choose a template')
    
    // In case the user chooses both options
    if (this.templateId != null && this.modules.length > 0) 
      throw Error('please choose only one option: choose modules manually or choose a template')

    const modulesObj = {}// TODO: find a better name. This object contains the optional params that go to the request body: templateId or an array of module names (string)

    if (this.templateId != null)
      modulesObj.templateId = this.templateId
    else if (this.modules.length != 0)
      modulesObj.moduleNames = this.modules

    //TODO: remove hardcoded url
    
    const rsp = await this.props.api.createBoardAsync('http://localhost:8080/boards', this.titleVal, this.descVal, modulesObj)
    console.log(rsp)
    const board = await rspToBoardAsync(rsp)
    debugger
    console.log(board)
    this.props.history.push(`/board`, {board})
  }

//TODO: is getting the ref of BoardTemplate and do: this.boardTemplate.updateTemplates() the best solution??

  render() {
    return (
      <>
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
          api={this.props.api}
          addToModules={moduleName => this.modules.push(moduleName)}
          activateTemplate= {templateId => this.templateId = templateId}
        />

        <Button
          content='Create' 
          primary 
          style={{marginTop: 10}} 
          onClick={this.submitCreateBoardReq}
        />
      </>
    )
  }
}

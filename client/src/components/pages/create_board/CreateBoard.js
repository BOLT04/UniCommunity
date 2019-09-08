import React, { Component } from 'react'

import rspToBoardAsync from '../../../service/mapper/board-mapper'

import routes from '../../../common/routes'

import CreateBoardPresenter from './CreateBoardPresenter'

export default class CreateBoard extends Component {
  titleVal = ''
  descVal = ''
  blackboardNames = [] //array<string>
  hasForum = false
  templateId = null

  onTitleChange = e => {
    e.preventDefault()
    this.titleVal = e.target.value
  }

  onDescriptionChange = e => {
    e.preventDefault()
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
  submitCreateBoardReq = async e => {
    e.preventDefault()
    console.log(this.descVal)
    console.log(this.titleVal)

    console.log(this.blackboardNames)
    console.log(this.templateId)

    // Validate user input
    // In case the user chooses neither options
    if (this.templateId == null && (this.blackboardNames.length === 0 && !this.hasForum))
      throw Error('please specify the modules manually or choose a template')
    
    // In case the user chooses both options
    if (this.templateId != null && (this.blackboardNames.length > 0 || this.hasForum)) 
      throw Error('please choose only one option: choose modules manually or choose a template')

    const optionalParams = {}

    if (this.templateId != null)
      optionalParams.templateId = this.templateId
    else if (this.blackboardNames.length > 0) {
      optionalParams.blackboardNames = this.blackboardNames
      optionalParams.hasForum = this.hasForum
    }

    const rsp = await this.props.api.createBoardAsync(
      this.props.location.state.serverHref,
      this.titleVal,
      this.descVal,
      optionalParams
    )
    const board = await rspToBoardAsync(rsp)
    
    board.id = 1 // TODO: remove when this is in server impl.
    
    this.props.history.push(routes.getBoardUri(board.id), { board })
  }

  //TODO: is getting the ref of BoardTemplate and do: this.boardTemplate.updateTemplates() the best solution??

  render() {
    return (
      <CreateBoardPresenter
        asyncRelativeFetch={this.props.asyncRelativeFetch}
        onTitleChange={this.onTitleChange}
        onDescriptionChange={this.onDescriptionChange}
        boardTemplateApi={this.props.api}
        addToModules={moduleName => this.blackboardNames.push(moduleName)}
        updateHasForum={hasForum => this.hasForum = hasForum}
        activateTemplate={templateId => this.templateId = templateId}
        createSubmitBtnOnClick={this.submitCreateBoardReq}
      />
    )
  }
}
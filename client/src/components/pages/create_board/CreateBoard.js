import React, { Component } from 'react'

import rspToBoardAsync from '../../../service/mapper/board-mapper'

import routes from '../../../common/routes'

import CreateBoardPresenter from './CreateBoardPresenter'
import { withUtilsConsumer } from '../../withUtilsConsumer'

class CreateBoard extends Component {
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

  /**
   * Makes a request to the API using the object received on props called api, to create a board.
   */
  submitCreateBoardReq = async e => {
    e.preventDefault()

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

    const messaging = this.props.firebase.messaging()
    const token = await messaging.getToken()
    const rsp = await this.props.api.createBoardAsync(
      this.props.location.state.serverHref,
      this.titleVal,
      this.descVal,
      token,
      optionalParams
    )
    const board = await rspToBoardAsync(rsp)

    this.props.history.push(routes.getBoardUri(board.id), { board })
  }

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

export default withUtilsConsumer(CreateBoard)
import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { asyncCreateBlackboardItem } from '../../../service/BlackBoardApi'

import routes from '../../../common/routes'
import CreateBoardApi from '../../../service/CreateBoardApi'
import CreateModuleItem from './CreateModuleItem'

export default class CreateBlackboardItem extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi).isRequired,
  }

  submitCreatePostHandler = async (title, content) => {
    const { state: locationState } = this.props.location

    const url = locationState.createBlackboardItemUrl
    const rsp = await asyncCreateBlackboardItem(url, title, content)
    const rspObj = await rsp.json()
  
    if (rspObj._links)      
      this.props.history.push(routes.getBoardUri(locationState.boardId))
  }

  render = () => (
    <CreateModuleItem
      header={`Create a new item on ${this.props.match.params.bbName}`}
      submitOnClick={this.submitCreatePostHandler}>
    </CreateModuleItem>
  )
}

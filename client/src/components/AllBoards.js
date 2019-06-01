import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { List } from 'semantic-ui-react'

import asyncCollectionRspToList from '../api/mapper/collectionJson-mapper'
import CreateBoardApi from '../api/CreateBoardApi'
import { BoardListItem } from './BoardListItem'
import ListLoader from './ListLoader'

import { COLLECTION_JSON } from '../common/constants'

export default class AllBoards extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi)
  }

  state = {
    boards: []
  }

  async componentDidMount() {
    const rsp = await this.props.asyncRelativeFetch(this.props.location.state.serverHref, COLLECTION_JSON)
    const boards = await asyncCollectionRspToList(rsp)
    debugger
    console.log(boards)

    this.setState({ boards })
  }

  renderBoards = () => (
    <List animated>
      { this.state.boards.map(b => <BoardListItem board={b} />)}
    </List>
  )

  render = () => (
      <ListLoader
        list={this.state.boards}
        emptyListHeaderMsg='No Boards available'
        emptyListMsg='Consider creating a board first.'
        render={this.renderBoards}
      />
  )
}

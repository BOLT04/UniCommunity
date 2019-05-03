import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { List, Loader } from 'semantic-ui-react'

import asyncCollectionRspToList from '../api/mapper/collectionJson-mapper'
import CreateBoardApi from '../api/CreateBoardApi'
import { BoardListItem } from './BoardListItem'

export default class CreateBoard extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi)
  }

  state = {
    boards: []
  }

  async componentDidMount() {
    const rsp = await this.props.api.getAllBoardsAsync(this.props.location.state.serverHref)
    const boards = await asyncCollectionRspToList(rsp)
    console.log(boards)

    this.setState({ boards })
  }

  render() {
    const { boards } = this.state

    const renderBoards = () => (
      <List animated>
        { boards.map(b => <BoardListItem board={b} />)}
      </List>
    )

    return (
      <>
        { boards.length == 0 
          ? <Loader active inline />
          : renderBoards()              
        }
      </>
    )
  }
}

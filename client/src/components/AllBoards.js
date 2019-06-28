import React from 'react'
import PropTypes from 'prop-types'

import { List } from 'semantic-ui-react'

import asyncCollectionRspToList from '../api/mapper/collectionJson-mapper'
import CreateBoardApi from '../api/CreateBoardApi'

import { BoardListItem } from './BoardListItem'
import ListLoader from './ListLoader'
import BookmarkableComponent from './BookmarkableComponent'

import { COLLECTION_JSON, httpStatus } from '../common/constants'
import routes from '../common/routes'
import { rels } from '../common/rels-registery'

export default class AllBoards extends BookmarkableComponent {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi)
  }

  state = {
    boards: []
  }

  constructor(props) {
    super(props)

    this.addServerHrefOf(rels.getBoards, { checkState: true })
  }

  async componentDidMount() {
    const a = this.addServerHrefOf(rels.getBoards)
    debugger
    if (a) {
      debugger
      await this.fetchData()
    }
  }

  async componentDidUpdate(prevProps) {
    if (this.props.home !== prevProps.home && this.addServerHrefOf(rels.getBoards))
        await this.fetchData()
  }

  async fetchData() {
    debugger
    const rsp = await this.props.asyncRelativeFetch(this.serverHref, COLLECTION_JSON)
    if (rsp.status == httpStatus.UNAUTHORIZED) {
      this.props.history.push(routes.login, { redirectTo: this.props.location.pathname })
      return
    }
    const boards = await asyncCollectionRspToList(rsp)

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

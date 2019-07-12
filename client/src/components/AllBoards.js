import React from 'react'
import PropTypes from 'prop-types'

import { List } from 'semantic-ui-react'

import asyncCollectionRspToList from '../api/mapper/collectionJson-mapper'
import CreateBoardApi from '../api/CreateBoardApi'

import { BoardListItem } from './BoardListItem'
import ListLoader from './ListLoader'
import Paginator from './Paginator'
import BookmarkableComponent from './BookmarkableComponent'

import { COLLECTION_JSON, httpStatus } from '../common/constants'
import routes from '../common/routes'
import { rels } from '../common/rels-registery'

export default class AllBoards extends BookmarkableComponent {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi)
  }

  constructor(props) {
    super(props)

    this.state = {}
    this.addServerHrefOf(rels.getBoards, { checkState: true })
  }

  async componentDidMount() {
    const a = this.addServerHrefOf(rels.getBoards)

    if (a) {
      await this.fetchData(this.serverHref)
    }
  }

  async componentDidUpdate(prevProps) {
    if (this.props.home !== prevProps.home && this.addServerHrefOf(rels.getBoards))
        await this.fetchData(this.serverHref)
  }

  async fetchData(url) {
    debugger
    const rsp = await this.props.asyncRelativeFetch(url, COLLECTION_JSON)
    if (rsp.status == httpStatus.UNAUTHORIZED) {
      this.props.history.push(routes.login, { redirectTo: this.props.location.pathname })
      return
    }
    const boards = await asyncCollectionRspToList(rsp)

    this.setState({ boards })
  }

  renderBoards = () => (
    <List animated>
      { this.state.boards.items.map(b => <BoardListItem board={b} />)}
    </List>
  )

  handlePagination = url => this.fetchData(url)

  render() {
    const { boards } = this.state || {}
    const list = boards && boards.items || null
    debugger
    return (
      <>
        <ListLoader
          list={list}
          emptyListHeaderMsg='No Boards available'
          emptyListMsg='Consider creating a board first.'
          render={this.renderBoards}
        />

        { boards &&
          <Paginator body={boards} onClick={this.handlePagination} />
        }
      </>
    )
  }
}

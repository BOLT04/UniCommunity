import React from 'react'
import PropTypes from 'prop-types'

import { Loader } from 'semantic-ui-react'

import rspToBoardAsync from '../../../service/mapper/board-mapper'
import { APPLICATION_HAL_JSON, httpStatus } from '../../../common/constants'
import { rels } from '../../../common/rels-registery'
import routes from '../../../common/routes'
import BookmarkableComponent from '../../common/BookmarkableComponent'

import ModulesView from './ModulesView'
import EditBoardButton from './EditBoardButton'
import Header from '../../Header'

import { withUtilsConsumer } from '../../withUtilsConsumer'

class BoardView extends BookmarkableComponent {
  static propTypes = {
    home: PropTypes.object
  }

  constructor(props) {
    super(props)

    const { board } = this.props.location.state || {}
    this.state = { board }
    this.addServerHrefOf(rels.getBoard, { 
      checkState: true,
      parseTemplate: this.parseTemplateUrl 
    })
  }

  async componentDidMount() {
    if (this.addServerHrefAux())
      await this.fetchData()
  }

  async componentDidUpdate(prevProps) {
    if (this.props.home !== prevProps.home && this.addServerHrefAux())
        await this.fetchData()
  }

  addServerHrefAux() {
    const options = {
      parseTemplate: this.parseTemplateUrl
    }
    return this.addServerHrefOf(rels.getBoard, options)
  }

  // This method is used to get a reference of itself, so this is defined with an arrow function to capture 'this'.
  parseTemplateUrl = url => {
    const regex = /{.*?}/g
    const pathParts = this.props.location.pathname.split('/')
    const boardId = pathParts[pathParts.length -1]

    return url.replace(regex, boardId)
  }

  async fetchData() {
    const rsp = await this.props.utilsObj.asyncRelativeFetch(this.serverHref, APPLICATION_HAL_JSON)
    if (rsp.status === httpStatus.UNAUTHORIZED) {
      this.props.history.push(routes.login, { redirectTo: this.props.location.pathname })
      return
    }

    const board = await rspToBoardAsync(rsp)
    this.setState({ board, loading: false })
  }

  updateBoard = newBoard => this.setState({ board: newBoard })

  renderBoard() {
    const { board } = this.state
    
    return (
      <>
        <Header 
          className='ui blue header'
          header={board.name}
          content={board.description}
        />
        { board.editBoardHref &&
            <EditBoardButton
              board={this.state.board}
              updateBoard={this.updateBoard} />
        }
     
        <div className='ui divider' />

        { board.modules && 
            <ModulesView board={board} />
        }
      </>
    )
  }

  render = () => (
    !this.state.board
      ? <Loader active inline />
      : this.renderBoard()
  )
}

export default withUtilsConsumer(BoardView)
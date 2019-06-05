import React, { Component } from 'react'
import PropTypes from 'prop-types'

import rspToBoardAsync from '../../api/mapper/board-mapper'
import { APPLICATION_HAL_JSON } from '../../common/constants'

import ModulesView from './ModulesView'
import Header from '../Header'

export default class BoardView extends Component {
  //TODO: how do I specify the entrance of the model (board) to this component? 
  //TODO: I dont want it to be in props.location.state.board because that is coupled to CreateBoard component!
  /*static propTypes = {
    board: PropTypes.object
  }*/
  constructor(props) {
    super(props)

    this.state = {
      board: this.props.location.state
    }
  }

  async componentDidMount() {
    const rsp = await this.props.asyncRelativeFetch(this.props.location.state.board.href, APPLICATION_HAL_JSON)
    const board = await rspToBoardAsync(rsp)

    this.setState({ board, loading: false })
  }

  //TODO: is there a better way of passing props than below? Like BoardHearder already have access to parent 
  //props?
  render() {
    console.log(this.props.match)
    const { board } = this.state

    return (
      <>
        <Header 
          className='ui blue header'
          header={board.name}
          content={board.description}
        />
        <div className="ui divider"></div>

        <ModulesView board={board} />
      </>
    )
  }
}
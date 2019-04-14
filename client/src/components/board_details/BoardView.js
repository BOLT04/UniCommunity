import React, { Component } from 'react'
import PropTypes from 'prop-types'

import BoardHeader from './BoardHeader'
import ModulesView from './ModulesView'

export default class BoardView extends Component {
  //TODO: how do I specify the entrance of the model (board) to this component? 
  //TODO: I dont want it to be in props.location.state.board because that is coupled to CreateBoard component!
  /*static propTypes = {
    board: PropTypes.object
  }*/

  //TODO: is there a better way of passing props than below? Like BoardHearder already have access to parent 
  //props?
  render() {
    return (
      <div >
        <BoardHeader board={this.props.location.state.board} />
        <div className="ui divider"></div>
        <ModulesView board={this.props.location.state.board} />
      </div>
      )
    }
}
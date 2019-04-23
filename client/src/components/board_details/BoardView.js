import React, { Component } from 'react'
import PropTypes from 'prop-types'

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
    const { board } = this.props.location.state

    return (
      <>
        <h4 className="ui blue header">{board.name}</h4>
        <p>{board.description}</p>
        <div className="ui divider"></div>

        <ModulesView board={board} />
      </>
    )
  }
}
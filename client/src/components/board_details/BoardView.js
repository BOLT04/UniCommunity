import React, { Component } from 'react'
import PropTypes from 'prop-types'

import ModulesView from './ModulesView'
import Header from '../Header'

export default class BoardView extends Component {
  //TODO: how do I specify the entrance of the model (board) to this component? 
  //TODO: I dont want it to be in props.location.state.board because that is coupled to CreateBoard component!
  /*static propTypes = {
    board: PropTypes.object
  }*/

  //TODO: is there a better way of passing props than below? Like BoardHearder already have access to parent 
  //props?
  render() {
    console.log(this.props.match)
    const { board } = this.props.location.state

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
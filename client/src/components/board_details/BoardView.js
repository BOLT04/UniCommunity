import React, { Component } from 'react'
import PropTypes from 'prop-types'

import BoardHeader from './BoardHeader'
import ModulesView from './ModulesView'

//const input = '# This is a header\n\nAnd this is a paragraph'
// <ReactMarkdown source={input} />
class BoardView extends Component {
  static propTypes = {
    board: PropTypes.object
  }

  //TODO: is there a better way of passing props than below? Like BoardHearder already have access to parent 
  //props?
  render() {
    return (
      <div >
        <BoardHeader board={this.props.board} />
        <div className="ui divider"></div>
        <ModulesView board={this.props.board} />
      </div>
      )
    }
  }
  
  export default BoardView

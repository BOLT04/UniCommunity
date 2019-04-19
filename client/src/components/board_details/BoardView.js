import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Route } from 'react-router-dom'

import { Tab, Header, Icon, Image, Menu, Segment, Sidebar, Grid } from 'semantic-ui-react'

import ModulesView from './ModulesView'
import Forum from './Forum'

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

    function buildPanes() {
      return [
        {
          menuItem: 'Modules',
          render: () =>
            <Tab.Pane attached={false}>
              <ModulesView board={board} />
            </Tab.Pane>
        },
        {
          menuItem: 'Forum',
          render: () =>
            <Tab.Pane attached={false}>
              <Forum />
            </Tab.Pane>
        }
      ]
    }
/*
    createPostHandler = () => {
      console.log(1)
    }
*/
    return (
      <>
        <h4 className="ui blue header">{board.name}</h4>
        <p>{board.description}</p>
        <div className="ui divider"></div>

      
            {/*
              <Tab menu={{ secondary: true, pointing: true }} panes={buildPanes()} />
            */}
          <ModulesView board={board} />
          
          {/*<Route exact path={`/${board.name}/forum/posts/new`} component={CreatePost} />*/}
      </>
    )
  }
}
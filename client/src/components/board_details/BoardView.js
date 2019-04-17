import React, { Component } from 'react'
import PropTypes from 'prop-types'

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

        <Grid celled>
          <Grid.Row>
            <Grid.Column width={3}>
              <h4>Actions</h4>
              <Sidebar.Pushable as={Segment}>
    <Sidebar as={Menu} animation='overlay' icon='labeled' inverted vertical visible width='thin'>
      <Menu.Item as='a'>
        <Icon name='home' />
        Forum
      </Menu.Item>
      <Menu.Item as='a'>
        <Icon name='gamepad' />
        Games
      </Menu.Item>
    </Sidebar>

    <Sidebar.Pusher>
      <Segment basic>
        <Header as='h3'>Application Content</Header>
        <Image src='/images/wireframe/paragraph.png' />
      </Segment>
    </Sidebar.Pusher>
  </Sidebar.Pushable>
            </Grid.Column>

            <Grid.Column width={13}>
            {/*
              <Tab menu={{ secondary: true, pointing: true }} panes={buildPanes()} />
            */}
            <ModulesView board={board} />
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </>
    )
  }
}
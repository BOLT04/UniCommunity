import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { withRouter } from 'react-router-dom'
import { Menu, Segment, Message, Transition } from 'semantic-ui-react'

import LogoutButton from '../LogoutButton'
import NavBarLink from './NavBarLink'

import auth from '../../service/auth'

import { APPLICATION_HAL_JSON } from '../../common/constants'
import relsRegistery from '../../common/rels-registery'

import { withUtilsConsumer } from '../withUtilsConsumer'

class NavBar extends Component {
  static propTypes = {
    navMenuUrl: PropTypes.string
  }

  constructor(props) {
    super(props)

    this.state = {
      visible: false,
      navMenu: {},
      activeItem: 'home'
    }
  }

  async componentDidMount() {
    await this.fetchData()

    const { firebase } = this.props
    const messaging = firebase.messaging()
    messaging.onMessage(payload => {
      this.setState({ payload, visible: true })
    })
  }

  async componentDidUpdate(prevProps) {
    if ((this.props.toReRender !== prevProps.toReRender) || this.props.toReRender)
      await this.fetchData()
  }

  async fetchData() {
    const rsp = await this.props.utilsObj.asyncRelativeFetch(this.props.navMenuUrl, APPLICATION_HAL_JSON)
    const rspObj = await rsp.json()
    const navMenu = rspObj._links

    this.setState({ navMenu })
  }

  /**
   * @param {object} body - The response body that resulted from a necessary GET request to some resource, in
   * order to build the client URL.
   */
  handleItemClick = (e, { name }, clientHref, serverHref, body) => {
    e.preventDefault()
    this.setState({ activeItem: name })

    // Redirect to the corresponding page
    this.props.history.push(clientHref, { serverHref, body })
  }

  handleDismiss = () => this.setState({ visible: false })

  decideNotificationRender() {
    const { payload } = this.state
    if (!payload) return null

    const { title, body: msg } = payload.notification
    const { notificationLevel } = payload.data
    if (notificationLevel === 'all')
      return (
        <Notification
          title={title}
          msg={msg}
          warning
          size='huge'
          onDismiss={this.handleDismiss}
        />
      )
    if (notificationLevel === 'priority')
      return (
        <Notification
          title={title}
          msg={msg}
          info
          onDismiss={this.handleDismiss}
        />
      )
    if (notificationLevel === 'none')
      return null
  }

  onLogoutClickHandler = async e => {
    auth.logout()

    await this.fetchData() // Update Navbar links

    // Redirect to home page
    this.props.history.push('/')
  }

  buildLinks() {
    const { navMenu, activeItem } = this.state

    const buildRightMenu = (reg, serverHref) => {
      return (
        <NavBarLink
          className='right'
          onClick={this.handleItemClick}
          reg={reg}
          serverHref={serverHref}
          activeItem={activeItem}
          utilsObj={this.props.utilsObj}
        />
      )
    }

    const leftMenuItems = []
    const rightMenuItems = []

    if (navMenu)
      Object.keys(navMenu)
        .forEach(prop => {
          const reg = relsRegistery[prop]
          
          if (reg) {
            if (reg.toDisplayOnRight)
              rightMenuItems.push({ reg, prop })
            else
              leftMenuItems.push({ reg, prop })
          }
        })

    return (
      <>
        {leftMenuItems.map(({ reg, prop }) => (
          <NavBarLink
            onClick={this.handleItemClick}
            reg={reg}
            serverHref={navMenu[prop].href}
            activeItem={activeItem}
            utilsObj={this.props.utilsObj}
          />
        ))
        }

        {rightMenuItems.map(({ reg, prop }) =>
          buildRightMenu(reg, navMenu[prop].href)
        )}
      </>
    )
  }
  
  render = () => (
    <>
      <Segment inverted size='tiny'>
        <Menu inverted compact secondary stackable>
          {this.buildLinks()}
          {auth.isAuthenticated() && 
              <LogoutButton onClickHandler={this.onLogoutClickHandler} />
          }
          
        </Menu>
      </Segment>
      <Transition visible={this.state.visible} animation='slide left' duration={500}>
        {this.decideNotificationRender()}
      </Transition> 
    </>
  )
}

const Notification = ({ msg, title, ...props}) => (
  <Message {...props}>
    <Message.Header>{title}</Message.Header>
    <p>{msg}</p>
  </Message>
)

export default withRouter(withUtilsConsumer(NavBar))
import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { withRouter } from 'react-router-dom'
import { Menu, Segment, Message, Transition } from 'semantic-ui-react'

import LogoutButton from './LogoutButton'

import NavBarApi from '../service/NavBarApi'
import auth from '../service/auth'

import { APPLICATION_HAL_JSON } from '../common/constants'
import relsRegistery from '../common/rels-registery'

import { withUtilsConsumer } from './withUtilsConsumer'

class NavBar extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(NavBarApi),
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
      console.log({ payload })
      this.setState({ payload, visible: true })
    }, e => console.error({ e }), c => console.log({ c }))
  }

  async componentDidUpdate(prevProps) {
    if (this.props.toReRender !== prevProps.toReRender)
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

  decideRender() {
    const { payload } = this.state
    if (!payload) return null
    if (payload.data.notificationLevel === '2')
      return (
        <Message warning size='huge' onDismiss={this.handleDismiss}>
          <Message.Header>{payload.notification.title}</Message.Header>
          <p>{payload.notification.body}</p>
        </Message>
      )
    if (payload.data.notificationLevel === '1')
      return (
        <Message info onDismiss={this.handleDismiss}>
          <Message.Header>{payload.notification.title}</Message.Header>
          <p>{payload.notification.body}</p>
        </Message>
      )
  }

  onLogoutClickHandler = async e => {
    auth.logout()
    // Redirect to home page
    await this.fetchData()
    this.props.history.push('/')

  }

  buildLinks() {
    const { navMenu, activeItem, visible } = this.state

    //TODO: right now this only supports one link with the property: "toDisplayOnRight", since it would create another div on the next link with the same prop.
    //TODO: later this probably receives an array of registeries to include multiple links on the right menu like: Search bar, Logout, Login, user profile, etc
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
    /*
        return Object.keys(navMenu)
          .map(prop => {
            const reg = relsRegistery[prop]
            if (reg)
              if (reg.toDisplayOnRight)
                rightMenuItems.push({ reg, href: navMenu[prop].href })
                return //buildRightMenu(reg, navMenu[prop].href)
              else
                return (
                  <Menu.Item
                    key={reg.name}
                    name={reg.name}
                    active={activeItem === reg.name}
                    onClick={this.handleItemClick}
                  >
                    <Link
                      as='div'
                      to={{
                        pathname: reg.clientHref,
                        state: { serverHref: navMenu[prop].href }
                      }}
                    >
                      {reg.name}
                    </Link>
                  </Menu.Item>
                )
          })
          */
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
        )
        }
        
        <Transition visible={visible} animation='slide left' duration={500}>
          {this.decideRender()}
        </Transition> 
      </>
    )
  }

  render = () => (
    <Segment inverted size='tiny'>
      <Menu inverted pointing secondary stackable>
        {this.buildLinks()}

        { auth.isAuthenticated() && 
            <LogoutButton onClickHandler={this.onLogoutClickHandler} />
        }
      </Menu>
    </Segment>
  )
}

class NavBarLink extends Component {

  constructor(props) {
    super(props)

    // Check if there needs to be further actions to get the built href value.
    // For example with the user profile link, it is necessary to get an identifier of the user resource in order
    // to build the client href (since the client URLs are not the same as the server URLs to prevent coupling)!
    const { reg } = props
    this.isFunction = typeof reg.clientHref === 'function'
    const href = this.isFunction ? '' : reg.clientHref

    this.state = { href }
  }

  onClickHandler = (e, data) => {
    const { onClick, reg, serverHref } = this.props
    const { href } = this.state

    onClick(e, data, href, serverHref, this.body)
  }

  async componentDidMount() {
    if (!this.isFunction) return

    const { reg, serverHref } = this.props
    const href = await this.buildHref(reg.clientHref, serverHref)
    this.setState({ href })
  }

  /**
   * 
   * @param {function} buildClientHref - Function that builds the client href. Descriptor (string) -> string.
   * The function takes the id necesary to build the complete href.
   * @param {*} serverHref - Server URL for the resource that we want to build a client URL. This resource needs
   * to provide an "id" property on the response payload, representing the resource identifier.
   */
  async buildHref(buildClientHref, serverHref) {
    const rsp = await this.props.utilsObj.asyncRelativeFetch(serverHref, APPLICATION_HAL_JSON)
    const body = await rsp.json()
    this.body = body // Save the response body to give to the component that will render a page for this resource

    return buildClientHref(body.id)
  }

  render() {
    const { reg, activeItem, className } = this.props
    const { href } = this.state
    
debugger
    return (
      <Menu.Item
        link
        href={href}
        className={className}
        key={reg.name}
        name={reg.name}
        active={activeItem === reg.name}
        onClick={this.onClickHandler}
      >
        {reg.render
          ? reg.render()
          : reg.name
        }
      </Menu.Item>
    )
  }
}

export default withRouter(withUtilsConsumer(NavBar))
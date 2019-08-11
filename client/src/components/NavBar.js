import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { withRouter } from 'react-router-dom'
import { Menu, Segment, Message, Transition } from 'semantic-ui-react'

import NavBarApi from '../service/NavBarApi'

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
      console.log(1)
      console.log({ payload })
      this.setState({ payload, visible:true })
    }, e => console.log({ e }), c => console.log({ c }))
  }

  async componentDidUpdate(prevProps) {
    if (this.props.reRender !== prevProps.reRender)
      await this.fetchData()
  }

  async fetchData() {
    const rsp = await this.props.utilsObj.asyncRelativeFetch(this.props.navMenuUrl, APPLICATION_HAL_JSON)
    const rspObj = await rsp.json()
    const navMenu = rspObj._links

    this.setState({ navMenu })
  }

  handleItemClick = (e, { name }, reg, serverHref) => {
    e.preventDefault()
    this.setState({ activeItem: name })

    // Redirect to the corresponding page
    this.props.history.push(reg.clientHref, { serverHref })
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
          />
        ))
        }

        {rightMenuItems.map(({ reg, prop }) =>
          buildRightMenu(reg, navMenu[prop].href)
        )
        }
        
            <Transition visible={visible} animation='slide left' duration={500}>
              { 
                this.decideRender()
              }
            </Transition> 
      </>
    )
  }

  render = () => (
    <Segment inverted size='tiny'>
      <Menu inverted pointing secondary stackable>
        {this.buildLinks()}
      </Menu>
    </Segment>
  )
}

class NavBarLink extends Component {
  onClickHandler = (e, data) => {
    const { onClick, reg, serverHref } = this.props
    onClick(e, data, reg, serverHref)
  }

  render() {
    const { reg, activeItem, className } = this.props
    return (
      <Menu.Item
        link
        href={reg.clientHref}
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
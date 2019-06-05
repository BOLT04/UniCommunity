import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'

import { Menu } from 'semantic-ui-react'

import NavBarApi from '../api/NavBarApi'

import { APPLICATION_HAL_JSON } from '../common/constants'
import relsRegistery from '../common/rels-registery'

export default class NavBar extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(NavBarApi),
    navMenuUrl: PropTypes.string
  }

  constructor(props) {
    super(props)

    this.state = {
      navMenu: {},
      activeItem: 'home'
    }
  }

  handleItemClick = (e, { name }) => this.setState({ activeItem: name })

  async componentDidMount() {
    await this.fetchData()
  }

  async componentDidUpdate(prevProps) {
    if (this.props.reRender !== prevProps.reRender)
      await this.fetchData()
  }

  async fetchData() {
    const rsp = await this.props.asyncRelativeFetch(this.props.navMenuUrl, APPLICATION_HAL_JSON)
    const rspObj = await rsp.json()
    const navMenu = rspObj._links

    this.setState({ navMenu })
  }

  buildLinks() {
    const { navMenu, activeItem } = this.state

    //TODO: right now this only supports one link with the property: "toDisplayOnRight", since it would create another div on the next link with the same prop.
    //TODO: later this probably receives an array of registeries to include multiple links on the right menu like: Search bar, Logout, Login, user profile, etc
    function buildRightMenu(reg, serverHref) {
      return (
        <Menu.Item
          className='right'
          key={reg.name}
          name={reg.name}
          active={activeItem === reg.name}
          onClick={this.handleItemClick}
        >
          <Link 
            to={{
              pathname: reg.clientHref,
              state: { serverHref }
            }}
            className='item'
          >
            {reg.name}
          </Link>
        </Menu.Item>
      )
    }
    
    return Object.keys(navMenu)
      .map(prop => {
        const reg = relsRegistery[prop]
        if (reg)
          if (reg.toDisplayOnRight)
            return buildRightMenu(reg, navMenu[prop].href)
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
  }

  render() {
    return (
      <Menu pointing secondary stackable>
        {this.buildLinks()}
      </Menu>
    )
  }
}

import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'

import { Input, Menu, Segment } from 'semantic-ui-react'

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

  //TODO:if the state is initialized here, then componentWillMount() wont be called...but why tho???
  /*state = {
    navMenu: {}
  }
*/
  async componentDidMount() {
    //const rsp = await this.props.api.fetchNavigationMenuAsync(this.props.navMenuUrl)
    await this.fetchData()
  }

  async componentDidUpdate(prevProps) {
    debugger
    if (this.props.reRender !== prevProps.reRender)
      await this.fetchData()
  }

  async fetchData() {
    debugger
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
          {/*
          <div className="item">
            <div className="ui icon input">
              <input type="text" placeholder="Search..." />
              <i className="search link icon"></i>
            </div>
          </div>
          */}

          <Link 
            to={{
              pathname: reg.clientHref,
              state: { serverHref }
            }}
            className='item'
          >
            {reg.name}
          </Link>
{/*
          <button className="ui red basic button">Logout</button>
*/  }
        </Menu.Item>
      )
    }
    
    return Object.keys(navMenu)
      .map((prop, index) => {// TODO: for now, the active item starts by being the first prop...this is assuming /home is the first prop
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
    console.log(`render: state: ${this.state}`)

    return (
      <Menu pointing secondary stackable>
        {this.buildLinks()}
        {/*
        <Link to="/" className="active item">
          Home
        </Link>
        <Link to="/board/create" className="item">
          Create Board
        </Link>
        <div className="right menu">
          <div className="item">
            <div className="ui icon input">
              <input type="text" placeholder="Search..." />
              <i className="search link icon"></i>
            </div>
          </div>

          {
            this.state.navMenu.login &&
              <Link to= {this.state.navMenu.login}>
                <button className="ui primary basic button">Log in</button>
              </Link>
          }

          <button className="ui red basic button">Logout</button>
        
        </div>
        */}
      </Menu>
    )
  }
}

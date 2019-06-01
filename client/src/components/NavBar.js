import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'

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
      navMenu: {}
    }
  }

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
    const { navMenu } = this.state

    //TODO: right now this only supports one link with the property: "toDisplayOnRight", since it would create another div on the next link with the same prop.
    //TODO: later this probably receives an array of registeries to include multiple links on the right menu like: Search bar, Logout, Login, user profile, etc
    function buildRightMenu(reg, serverHref) {
      return (
        <div className="right menu" key={reg.name}>
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
        </div>
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
              <Link 
                key={reg.name} 
                to={{
                  pathname: reg.clientHref,
                  state: { serverHref: navMenu[prop].href }
                }}
                className={`${index == 0 ? 'active' : ''} item`}
              >
                {reg.name}
              </Link>
            )
      })
  }

  render() {
    console.log(`render: state: ${this.state}`)
    return (
      <div className="ui secondary stackable menu">
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
      </div>
    )
  }
}

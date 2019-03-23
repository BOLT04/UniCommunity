import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from "react-router-dom"

import NavBarApi from '../api/NavBarApi'

export default class NavBar extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(NavBarApi)
  }

  state = {
    navMenu: {}
  }

 componentDidMount() {
    //const rsp = await this.props.api.getNavigationMenu()
    //const navMenu = rsp._links
    console.log(`componentDidMount 1: ${this.state}`)
    this.setState({ navMenu: 1 })
    console.log(`componentDidMount 2: ${this.state}`)
  }

  render() {
    console.log(`render: state: ${this.state}`)
    return (
      <div className="ui secondary stackable menu">
        <Link to="/" className="active item">
          Home
        </Link>
        <a className="item">
          Messages
        </a>
        <Link to="/board/create" className="item">
          Create Board
        </Link>
        <Link to="/board" className="item">
          Board
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
          
          <button className="ui secondary basic button">Sign up</button>

          <button className="ui red basic button">Logout</button>

        </div>
      </div>
    )
  }
}

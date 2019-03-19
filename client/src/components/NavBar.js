import React, { Component } from 'react'
import { Link } from "react-router-dom"

class NavBar extends Component {
  render() {
    return (
      <div className="ui secondary stackable menu">
        <a className="active item">
          Home
        </a>
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
          
          <Link to="/login">
            <button className="ui primary basic button">Log in</button>
          </Link>
          <button className="ui secondary basic button">Sign up</button>  

          <button className="ui red basic button">Logout</button>        

        </div>
      </div>
    )
  }
}

export default NavBar

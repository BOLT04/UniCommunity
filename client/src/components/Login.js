import React, { Component } from 'react'
import { Link } from "react-router-dom"

import './css/Login.css'

export default class Login extends Component {
  render() {
    return (//TODO: why aligned twice and middle center?
      <div className="ui middle aligned center aligned grid">
        <div className="column">
          <h2 className="ui teal header">
            <img src={`${process.env.PUBLIC_URL}/img/logo.png`} className="image"></img>
            <div className="content">
              Log-in
            </div>
          </h2>

          <form className="ui large form">
            <div className="ui stacked segment">
              <div className="field">
                <div className="ui left icon input">
                  <i className="user icon"></i>
                  <input type="text" name="username" placeholder="Username" />
                </div>
              </div>
              <div className="field">
                <div className="ui left icon input">
                  <i className="lock icon"></i>
                  <input type="password" name="password" placeholder="Password" />
                </div>
              </div>
              <div className="ui fluid large teal submit button">Login</div>
            </div>
          </form>

          <div className="ui message">
            New to us? 
            <Link to="/signup" className="item"> Sign up</Link>
          </div>
        </div>
      </div>
    )
  }
}

import React, { Component } from 'react'
import { Link } from "react-router-dom"

import { Input, Form, TextArea, Button } from 'semantic-ui-react'
import './css/Login.css'

export default class Login extends Component {
  
  constructor() {
    this.usernameVal = ''
    this.passVal = ''
    this.onUsernameChange = this.onChangeInput.bind(this, 'usernameVal')
    this.onPasswordChange = this.onChangeInput.bind(this, 'passVal')
  }

  onChangeInput(propName, e) {
    this[propName] = e.target.value
  }

  render() {
    return (
      <div className="ui middle aligned center aligned grid">
        <div className="column">
          <h2 className="ui teal header">
            <img src={`${process.env.PUBLIC_URL}/img/logo.png`} className="image"></img>
            <div className="content">
              Log-in
            </div>
          </h2>

{/*
          <Form>
            <Form.Field>
              <label>Username</label>
              <div className="ui left icon input">
                <i className="user icon"></i>
                <Input
                  name='username'
                  placeholder='Username'
                  onChange={this.onUsernameChange}
                />
              </div>
            </Form.Field>
            <Form.Field>
              <label>Password</label>
              <div className="ui left icon input">
                <i className="lock icon"></i>
                <Input
                  name='password'
                  placeholder='Password'
                  onChange={this.onPasswordChange}
                />
              </div>
            </Form.Field>
          </Form>
          */}
          <form className="ui large form">
            <div className="ui stacked segment">
              <div className="field">
                
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

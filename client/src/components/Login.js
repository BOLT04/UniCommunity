import React, { Component } from 'react'
import { Link } from "react-router-dom"

import { Input, Form, TextArea, Button } from 'semantic-ui-react'
import './css/Login.css'

import auth from './auth'

export default class Login extends Component {
  
  constructor() {
    this.usernameVal = ''
    this.passVal = ''
    this.onUsernameChange = this.onChangeInput.bind(this, 'usernameVal')
    this.onPasswordChange = this.onChangeInput.bind(this, 'passVal')

    this.state = {}
  }

  onChangeInput(propName, e) {
    this[propName] = e.target.value
  }

  submitLoginHandler = e => {
    if (auth.login())
      this.props.location.push('/') // Redirect to home page
    else
      this.setState({})// todo: finish
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
              <div className="ui fluid large teal submit button" onClick={this.submitLoginHandler}>
              Login
              </div>
            </div>
          </form>
        </div>

        <div class="ui negative message">
          <i class="close icon"></i>
          <div class="header">
            Your credentials are wrong
          </div>
        </div>
      </div>
    )
  }
}

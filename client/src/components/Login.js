import React, { Component } from 'react'

import ErrorMessage from './common/ErrorMessage'

import './css/Login.css'

import auth from '../service/auth'
import routes from '../common/routes'

export default class Login extends Component {

  constructor(props) {
    super(props)

    this.emailVal = ''
    this.passVal = ''
    this.onEmailChange = this.onChangeInput.bind(this, 'emailVal')
    this.onPasswordChange = this.onChangeInput.bind(this, 'passVal')

    this.state = {}
  }

  onChangeInput(propName, e) {
    this[propName] = e.target.value
  }

  submitLoginHandler = async e => {
    try {
      const links = await auth.asyncLogin(this.props.location.state.serverHref, this.emailVal, this.passVal)
      if (links) {
        this.props.reRender() // Update Navbar

        const redirectPath = this.props.location.state.redirectTo || routes.home
        this.props.history.push(redirectPath)
      }
    } catch (error) {
      if (error.json) {
        const errorBody = await error.json()
        let newError = new Error('Authentication with given credentials failed!')
        if (errorBody.title)
          newError = new Error(errorBody.title)
        
        this.setState({ error: newError })
      } else
        this.setState({ error })
    }
  }

  render() {
    const { error } = this.state

    return (
      <>
        <div className="ui middle aligned center aligned grid">
          <div className="column">
            <h2 className="ui teal header">
              <img src={`${process.env.PUBLIC_URL}/img/logo.png`} className="image"></img>
              <div className="content">
                Log-in
            </div>
            </h2>
            <form className='ui large form'>
              <div className='ui stacked segment'>
                <div className='field'>
                  <div className='ui left icon input'>
                    <i className='user icon' />
                    <input
                      type='text'
                      name='email'
                      placeholder='Email'
                      onChange={this.onEmailChange} />
                  </div>
                </div>
                <div className='field'>
                  <div className='ui left icon input'>
                    <i className='lock icon' />
                    <input
                      type='password'
                      name='password'
                      placeholder='Password'
                      onChange={this.onPasswordChange} />
                  </div>
                </div>
                <div className='ui fluid large teal submit button' onClick={this.submitLoginHandler}>
                  Login
              </div>
              </div>
            </form>
          </div>

          {error && <ErrorMessage error={error} />}
          </div>
      </>
    )
  }
}

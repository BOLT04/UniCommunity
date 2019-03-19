import React, { Component } from 'react'


class Login extends Component {
  render() {
    return (//TODO: why aligned twice and middle center?
      <div className="ui middle aligned center aligned grid">
        <div className="column">
          <h2 className="ui teal header">
            <img src="../../public/img/logo.png" className="image"></img>
            <div class="content">
              Log-in
            </div>
          </h2>

          <div className="ui stacked segment">
            <div className="field">
              <div className="ui left icon input">
                <i className="user icon"></i>
                <input type="text" name="email" placeholder="E-mail address" />
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

          <div class="ui message">
            New to us? <a href="#">Sign Up</a>
          </div>
        </div>
      </div>
    )
  }
}

export default Login

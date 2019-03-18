import React, { Component } from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'

import NavBar from './NavBar'
import Footer from './Footer'
//import Login from './Login'
import CreateBoard from './CreateBoard'
//import Board from './Board'

class App extends Component {
  render() {
    return (
      <Router>   
        <div className="App">
          <div className="ui container">
            <NavBar /> 
            {/*
            <Route path="/login" component={Login}/>
            */}
            <Route path="/board/create" component={CreateBoard}/>
            
            {/*<Route path="/board" component={Board}/> */}
          </div>

          <Footer />
        </div> 
      </Router>
    )
  }
}

export default App

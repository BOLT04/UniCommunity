import React, { Component } from 'react'
import { BrowserRouter, Route } from 'react-router-dom'

import NavBar from './NavBar'
import Footer from './Footer'
import Login from './Login'
import CreateBoard from './CreateBoard'
import BoardView from './board_details/BoardView'
import BackToTopButton from './BackToTopButton'
import Home from './home_page/Home'
import CreatePost from './post/CreatePost'

import NavBarApiMock from '../api/NavBarApiMock'
import HomeApiMock from '../api/HomeApiMock'
import CreateBoardApiMock from '../api/CreateBoardApiMock'

//TODO: The App component might have too many responsabilities, because it has the one commented below
//TODO: plus it serves as the Service Locator, the one responsible to give all components their dependencies,
// and we are currently instantiating the API mocks in here...
/**
 * This is the root component of the application (used in index.js).
 * The responsibility of this component is to setup the React Router with all Routes and their components.
 */
export default class App extends Component {
  render() {
    return (
      <BrowserRouter>   
        <div className="App">
          <div className="ui container">
            <NavBar api={new NavBarApiMock()} /> 
            
            <Route exact path="/login" component={Login} />

            <Route exact path="/post" component={CreatePost} />
            
            <Route exact path="/board/create" render={props => 
              <CreateBoard {...props} api={new CreateBoardApiMock()} />} 
            />
            {/* //TODO: this boardDummy will not be here bc we dont have the board object here, only
            when the user clicks on button createBoard of CreateBoard component...so this lambda
            below will probably be removed */}
            <Route exact path="/board" render={props => 
              <BoardView {...props} />} 
            />
          </div>
          <Route exact path="/" render={props => 
              <Home {...props} api={new HomeApiMock()} />} 
          />
          
          {/* //TODO: THESE componentes were buggy so are not being used for the moment
          <Footer />
          <BackToTopButton />
          */}
        </div> 
      </BrowserRouter>
    )
  }
}

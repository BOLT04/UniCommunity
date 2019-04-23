import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { BrowserRouter, Route } from 'react-router-dom'

import NavBar from './NavBar'
import Footer from './Footer'
import Login from './Login'
import CreateBoard from './CreateBoard'
import BoardView from './board_details/BoardView'
import BackToTopButton from './BackToTopButton'
import Home from './home_page/Home'
import CreatePost from './post/CreatePost'

import HomeApi from '../api/HomeApi'

import NavBarApiImpl from '../api/NavBarApiImpl'
import CreateBoardApiImpl from '../api/CreateBoardApiImpl'

import relsRegistery from '../common/rels-registery'

//TODO: The App component might have too many responsabilities, because it has the one commented below
//TODO: plus it serves as the Service Locator, the one responsible to give all components their dependencies,
// and we are currently instantiating the API mocks in here...
/**
 * This is the root component of the application (used in index.js).
 * The responsibility of this component is to setup the React Router with all Routes and their components.
 */
export default class App extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(HomeApi)
  }

  state = {
    home: {}
  }

  async componentDidMount() {
    const rsp = await this.props.api.fetchHomeAsync()
    const rspObj = await rsp.json()

    const home = {}
    //TODO: move this to another place: This code is coupled to hal+json bc of the links 
    if (rspObj._links) {
      Object
        .keys(rspObj._links)
        .forEach(prop => {
          const reg = relsRegistery[prop]

          if (reg) 
            home[reg.propName] = rspObj._links[prop].href     
        })
    }

    if (rspObj._embedded) {
      //todo: use this for feed
    }

    this.setState({ home })
  }

  render() {
    const createBoardApi = new CreateBoardApiImpl()
    const { home } = this.state

    //TODO: this component depends on the propnames defined in rels-registery because it knows navMenuUrl...
    //TODO: is there a way to avoid this dependency?
    return (
      <BrowserRouter>   
        <div className="App">
          <div className="ui container">
            {home.navMenuUrl && 
              <NavBar
                navMenuUrl={home.navMenuUrl}
                api={new NavBarApiImpl()} /> 
            }

            <Route exact path="/login" component={Login} />

            <Route exact path="/posts/new" render={props => 
              <CreatePost {...props} api={createBoardApi} />} 
            />
            
            <Route exact path="/board/create" render={props => 
              <CreateBoard {...props} api={createBoardApi} />} 
            />

            <Route exact path="/board" render={props => 
              <BoardView {...props} />} 
            />
          </div>

          <Route exact path="/" render={props => 
            <Home {...props} api={this.props.api} />} 
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

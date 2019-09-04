import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import ProtectedRoute from './ProtectedRoute'

import NavBar from './NavBar'
import Footer from './Footer'
import Login from './Login'

import UserProfile from './pages/user_profile/UserProfile'
import CreateBoard from './pages/create_board/CreateBoard'
import AllBoards from './pages/list_boards/AllBoards'
import BoardView from './pages/board_details/BoardView'
import Home from './pages/home/Home'

import CreatePost from './pages/create_module_item/CreatePost'
import PostDetails from './pages/post_details/PostDetails'
import CreateBlackboardItem from './pages/create_module_item/CreateBlackboardItem'

import HomeApi from '../service/HomeApi'
import asyncRspToHome from '../service/mapper/home-mapper'

import NavBarApiImpl from '../service/NavBarApiImpl'
import CreateBoardApiImpl from '../service/CreateBoardApiImpl'

import { asyncRelativeHttpRequest } from '../common/common'
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
    reRenderNavBar : false,
    home: {}
  }

  async componentDidMount() {
    try {
      const rsp = await this.props.api.fetchHomeAsync()
      const home = await asyncRspToHome(rsp)

      const homeString = JSON.stringify(home)
      sessionStorage.setItem('home', homeString)

      this.setState({ home })
    } catch(e) {
      console.log({e})
      alert('Couldn\'t contact the server')//TODO: render an error page instead of this 
    }
  }

  reRenderNavBar = () => {
    this.setState({ reRenderNavBar: true })
  }

  render() {
    const createBoardApi = new CreateBoardApiImpl()
    const { home } = this.state

    //TODO: this component depends on the propnames defined in rels-registery because it knows navMenuUrl...
    //TODO: is there a way to avoid this dependency?
    return (
      <BrowserRouter>   
        <div className='App' style={{ display:'flex', minHeight:'100vh', flexDirection:'column' }}>
          <div className='ui container' style={{ flex: 1 }}>
            {home.navMenuUrl && 
              <NavBar
                reRender= {this.state.reRenderNavBar}
                navMenuUrl={home.navMenuUrl}
                asyncRelativeFetch={this.props.asyncRelativeFetch}
                api={new NavBarApiImpl()} /> 
            }

            <Switch>
              <Route exact path='/login' render={props =>
                <Login
                  {...props}
                  reRender={this.reRenderNavBar} />
              } />
              
              <Route exact path='/boards' render={props => 
                <AllBoards 
                  {...props}
                  home={home}
                  api={createBoardApi}
                  asyncRelativeFetch={this.props.asyncRelativeFetch} />} 
              />

              <ProtectedRoute exact path='/boards/new' render={props => 
                <CreateBoard 
                  {...props}
                  api={createBoardApi}
                  asyncRelativeFetch={this.props.asyncRelativeFetch} />} 
              />
              
              <Route path='/boards/:boardId/:bbName/blackboardItem/new' render={props => 
                <CreateBlackboardItem 
                  {...props}
                  asyncRelativeFetch={this.props.asyncRelativeFetch}
                  asyncRelativeHttpRequest={asyncRelativeHttpRequest} />
              }/>

              <Route exact path='/boards/:id' render={props => 
                <BoardView {...props} 
                  asyncRelativeFetch={this.props.asyncRelativeFetch}
                  asyncRelativeHttpRequest={asyncRelativeHttpRequest} />} 
              />

{/*//TODO: The URL for CreatePost should be /boards/:id/posts/new, since its an new post in the context of a board*/}
              <Route exact path='/posts/new' render={props => 
                <CreatePost {...props} api={createBoardApi} />} 
              />

              <Route exact path='/boards/:boardId/posts/:postId' render={props => 
                <PostDetails
                  {...props}
                  api={createBoardApi}
                  asyncRelativeFetch={this.props.asyncRelativeFetch} />} 
              />
              
              <Route exact path='/user/:id' render={props => 
                <UserProfile
                  {...props}
                  asyncRelativeFetch={this.props.asyncRelativeFetch} />} 
              />

            </Switch>
          </div>

{/*//todo: Since App.js is getting too big, a Home component will be created to substitute some features of
//todo: this App.js, and the component below will be called HomeContent, since it displays the content of the home page (img, etc) */}
          <Switch>
            <Route exact path='/' render={props => 
              <Home {...props} api={this.props.api} />} 
            />

{/*            <Route exact path='*' component={() => '404 NOT FOUND. Please visit the Home page at: /'} />*/}
          </Switch>
          
          {
          <Footer />
          /* //TODO: THESE componentes were buggy so are not being used for the moment
          <BackToTopButton />
          */}
        </div> 
      </BrowserRouter>
    )
  }
}
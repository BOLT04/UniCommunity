import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import ProtectedRoute from './ProtectedRoute'

import NavBar from './navbar/NavBar'
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

import CreateBoardApi from '../service/CreateBoardApi'

import { asyncRelativeHttpRequest } from '../common/common'

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

      sessionStorage.setObject('home', home)

      this.setState({ home })
    } catch(e) {
      alert('Couldn\'t contact the server')
    }
  }

  reRenderNavBar = reRenderNavBar => {
    this.setState({ reRenderNavBar })
  }

  render() {
    const createBoardApi = new CreateBoardApi()
    const { home } = this.state

    return (
      <BrowserRouter>   
        <div className='App' style={{ display:'flex', minHeight:'100vh', flexDirection:'column' }}>
          <div className='ui container' style={{ flex: 1 }}>
            {home.navMenuUrl && 
              <NavBar
                toReRender= {this.state.reRenderNavBar}
                reRender={this.reRenderNavBar}
                navMenuUrl={home.navMenuUrl}
                asyncRelativeFetch={this.props.asyncRelativeFetch}
              /> 
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

          <Switch>
            <Route exact path='/' render={props => 
              <Home {...props} api={this.props.api} />} 
            />
          </Switch>
          
          <Footer />
        </div> 
      </BrowserRouter>
    )
  }
}
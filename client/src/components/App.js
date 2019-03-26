import React, { Component } from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'

import NavBar from './NavBar'
import Footer from './Footer'
import Login from './Login'
import CreateBoard from './CreateBoard'
import BoardView from './board_details/BoardView'
import BackToTopButton from './BackToTopButton'
import Home from './Home'
import CreatePost from './post/CreatePost'

import NavBarApiMock from '../api/NavBarApiMock'

const boardDummy = {
  name: "PDM",
  modules: [
    {
      name: "Sumarios",
      content: [
        {
          name: "18/02/2019 - Course introduction",
          text: `* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)
          `
        },
        {
          name: "20/02/2019 - Designing Web APIs: Introduction",
          text: `* Web APIs (or HTTP APIs): Concept and Motivation\n* The [Architecture of the World Wide Web](https://www.w3.org/TR/webarch/)\n* The HTTP protocol: Introduction\n* Documentation:\n  * ["Introduction to Web APIs"](https://github.com/isel-leic-daw/1819v-public/wiki/Web-APIs)\n  * ["Designing evolvable Web APIs: Chapter 1"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch01.html)`
        }
      ]
    },
    {
      name: "Sumarios",
      content: [
        {
          name: "18/02/2019 - Course introduction",
          text: `* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)
          `
        }
      ]
    }
  ]
}

/**
 * This is the root component of the application (used in index.js).
 * The responsibility of this component is to setup the React Router with all Routes and their components.
 */
export default class App extends Component {
  render() {
    return (
      <Router>   
        <CreatePost />
        <div className="App">

          <div className="ui container">
            <NavBar api={new NavBarApiMock()} /> 
            
            <Route exact path="/login" component={Login} />
            
            <Route exact path="/board/create" component={CreateBoard} />
            {/* //TODO: this boardDummy will not be here bc we dont have the board object here, only
            when the user clicks on button createBoard of CreateBoard component...so this lambda
            below will probably be removed */}
            <Route exact path="/board" render={(props) => <BoardView {...props} board={boardDummy} />} />
          </div>
          <Route exact path="/" component={Home} />

          <Footer />
          <BackToTopButton />
        </div> 
      </Router>
    )
  }
}

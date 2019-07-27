import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Image, Header } from 'semantic-ui-react'

import HomeApi from '../../../service/HomeApi'
import HomeStepsContent from './HomeStepsContent'
import Feed from './feed/Feed'

import './css/Home.css'

export default class Home extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(HomeApi)
  }

  constructor(props) {
    super(props)
    
    this.state = {
      feedItemList: []
    }
  }

  async componentWillMount() {
    const rsp = await this.props.api.fetchHomeAsync()

    const feedItemList = await rspToFeedItemListAsync(rsp)
/*
    const feedItemList = [
      {
        name: "Anuncio 1", 
        shortDesc: 'Avaliação das Resoluções das Séries de Exercícios e Notas Finais da Turma LI51D', 
        author: "Carlos Martins",
        feedItemHref: "/boards/DAW-1819-v/announcements/1",
        userProfileHref: "/users/101/profile"
      }, 
      {
        name: "A",
        author: "Jose simao"}
    ]
*/
    this.setState({
      feedItemList
    })
  }

/**
 * Right now i'm stuck. I dont know where i should make the api call, ctor or componentWillMount. The problem
 * with the second is that i need state and i' not sure if the response of the api can be seen as state....
 */


  componentDidMount() {
    //const rsp = await this.props.api.getNavigationMenu()
    //const navMenu = rsp._links
    /*
    console.log(`componentDidMount 1: ${this.state}`)
    this.setState({ navMenu: 1 })
    console.log(`componentDidMount 2: ${this.state}`)
    */
  }

  // TODO: is the image responsive?
  render() {

    return (
      <div className="home">
        <Image src={`${process.env.PUBLIC_URL}/img/background-study.jpg`} />
        <Header className="centered-top" as="h1">UniCommunity</Header>

        <HomeStepsContent />

        {/* //TODO: not fully implemented on the server yet
        <Feed feedItemList={this.state.feedItemList} />
        */}
      </div>
    )
  }
}

// Auxiliary functions
/**
 * If in the response isn't included the feed or a way to get it, or if there is an error with any part of the
 * response, then this function returns undefined.
 * @param {object} rsp Represents the response of the API that comes in HAL+JSON format.
 */
async function rspToFeedItemListAsync(rsp) {//TODO: maybe move these constants strings to another file. Like how its done on the server
  const contentType = rsp.headers.get('Content-Type')
  let feedItemList = undefined

  if (contentType === 'application/hal+json') { 
    // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
    const body = await rsp.json()
    
    if (body._links) {
      Object
        .keys(body._links)
        .forEach(prop => {
          if (prop.includes('/')) { // TODO: does this violate the objective of HATEOAS? how do I avaliate the prop, like how do I know if its a defined link relation or custom?
            // Then this link relation is an URI
            //TODO:actually...this request to the entry point is probably not done in the Home component...bc even if i have the response to the Nav, I cant render the NavBar component here...
          }
        })
    }

    // Check if the feed rel is present
    if (body._embedded) {
      const feedArr = body._embedded['http://localhost:8080/rels/feed'] // TODO: maybe this auxiliary variable isnt needed
      if (feedArr) 
        feedItemList = feedArr.map(halItemToFeedItem)
    
    }
  }

  return feedItemList
}

//TODO: again...the question remains: can the client have knowlegde of the custom link relation '/rels/userProfile'?
function halItemToFeedItem ({ name, shortDesc, author, _links }) {
  return {
    name,
    shortDesc,
    author,
    feedItemHref: _links.self.href,
    userProfileHref: _links['/rels/userProfile'].href
  }
}
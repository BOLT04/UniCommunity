import React, { Component } from 'react'

import { Image, Header } from 'semantic-ui-react'

import HomeStepsContent from './HomeStepsContent'

import './css/Home.css'

export default class Home extends Component {
  // TODO: is the image responsive?
  render() {
    return (
      <div className="home">
        <Image src={`${process.env.PUBLIC_URL}/img/background-study.jpg`} />
        <Header className="centered-top" as="h1">UniCommunity</Header>

        <HomeStepsContent />
      </div>
    )
  }
}

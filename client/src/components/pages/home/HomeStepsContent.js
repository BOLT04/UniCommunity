import React, { Component } from 'react'

import { Image, Header, Icon, Step } from 'semantic-ui-react'

import '../css/Home.css'

export default class Home extends Component {
  // TODO: is the image responsive?
  render() {
    return (
      <div className="ui vertical stripe segment">
          <div className="ui middle aligned stackable grid container">
            <div className="row">
              <div className="eight wide column">
                <h3 className="ui header">As a Teacher</h3>
                <Step.Group vertical size="big">
                  <Step href='/signup'>
                    <Icon name='signup' />
                    <Step.Content>
                      <Step.Title>Create an account</Step.Title>
                    </Step.Content>
                  </Step>

                  <Step>
                    <Icon name='clipboard' />
                    <Step.Content>
                      <Step.Title>Create a Board</Step.Title>
                      <Step.Description>Specify the modules of the course you are creating and more options</Step.Description>
                    </Step.Content>
                  </Step>

                  <Step>
                    <Icon name='dashboard' />
                    <Icon name='bell outline' />
                    <Icon name='comments' />
                    <Step.Content>
                      <Step.Title>Enjoy teaching</Step.Title>
                      <Step.Description>Create announcements; summaries of each lesson; publish resources; answer questions in the forum</Step.Description>
                    </Step.Content>
                  </Step>
                </Step.Group>
              </div>
             
            </div>
            
          </div>
        </div>
    )
  }
}

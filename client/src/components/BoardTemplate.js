import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Grid, Checkbox } from 'semantic-ui-react'

import './css/BoardTemplate.css'

class BoardTemplate extends Component {
  static propTypes = {
    templates: PropTypes.arrayOf(PropTypes.string)
  }

  //TODO: this is subject to change since the name of the template might not be the identifier (maybe an id INSTEAD)
  //TODO: dummy data!!!
  templates = {
    "Teacher Template": [
      "Sumarios", "Anuncios", "Forum", "Recursos"
    ],
    "Basic Template": [
      "Anuncios"
    ]
  }

  componentDidMount() {
    //TODO: make a request to the api to know the available templates and their info
    /*GET /api/user/BOLT/board+templates
    response: [
      {
        name: "Teacher Template",
        modules: [
          "Sumarios", "Anuncios"...
        ]
      },
      {
        name: "Basic Template",
        modules: [
          "Anuncios"
        ]
      }
      ...
    ]
    */
  }

  onChangeCheckBox = e => {
    console.log(e.target.innerHTML) // TODO: is there a better way of doing this?
    //this.props.templates.push()
  }

  render() {
    return (
      <div>
        <h5 className="ui header">Templates</h5>
        <Grid>
          <Grid.Row>
            <Grid.Column width={8} className="template-item" onClick={this.activateTemplate}>
              {/*
              <Image src='/images/wireframe/paragraph.png' />
              */
              }
              Teacher Template
            </Grid.Column>
            <Grid.Column width={8} className="template-item" onClick={this.activateTemplate}>
              Basic Template
            </Grid.Column>
          </Grid.Row>

          <Grid.Row>
            <Grid.Column width={8} className="template-item" onClick={this.activateTemplate}>
              1 Template
            </Grid.Column>
            <Grid.Column width={8} className="template-item" onClick={this.activateTemplate}>
              Teacher2 Template
            </Grid.Column>
          </Grid.Row>
        </Grid>

        <h5 className="ui header">Or Choose manually</h5>
        <Grid>
          <Grid.Row>
            <Grid.Column width={8}>
              <Checkbox label='Sumarios' onChange={this.onChangeCheckBox}/>
            </Grid.Column>
            <Grid.Column width={8}>
              <Checkbox label='Recursos' />
            </Grid.Column>
            <Grid.Column width={8}>
              <Checkbox label='Bibliografia' />
            </Grid.Column>
          </Grid.Row>

          <Grid.Row>
            <Grid.Column width={8}>
              <Checkbox label='Anuncios' />
            </Grid.Column>
            <Grid.Column width={8}>
              <Checkbox label='Forum' />
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </div>
    )
  }

  //TODO: Do I need to make all this code to have clickable templates...I need to manage this functionality
  //TODO: or does it exist already made?
  activateTemplate = e => {
    const activeElem = document.querySelector('.template-item-active')

    // Deactivate the current template.
    if (activeElem)
      activeElem.classList.remove('template-item-active')
    
    if (e.target !== activeElem) { // then it means the user choose another template
      e.target.classList.remove('template-item-active:hover')
      e.target.classList.add('template-item-active')  
    }
  }

  updatePropsTemplates() {
    //TODO: is this valid duplicated code (redundant)?
    const activeElem = document.querySelector('.template-item-active') 
    if (activeElem)
      this.templates[activeElem.innerHTML]
        .forEach(templateName => this.props.templates.push(templateName))
    else
    // TODO: is there a better way of getting all checked boxes ?
      document.querySelectorAll(".ui.checkbox.checked label")
        .forEach(label => this.props.templates.push(label.innerHTML))
  }
}




export default BoardTemplate

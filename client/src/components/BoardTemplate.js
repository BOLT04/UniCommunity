import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Grid, Checkbox } from 'semantic-ui-react'

import './css/BoardTemplate.css'

import rspToTemplatesAsync from '../api/mapper/template-mapper'

export default class BoardTemplate extends Component {
  static propTypes = {
    modules: PropTypes.arrayOf(PropTypes.string),
    activateTemplate: PropTypes.func
  }

  constructor(props) {
    super(props)
    this.state = {
      templates: []
    }
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

  async componentDidMount() {
    const rsp = await this.props.api.getTemplatesAsync('http://localhost:8080/templates') //TODO: remove hardcoded url
    //TODO: catch error in case the promise is rejected
    const templates = await rsp.json()
    console.log(templates)
    
    this.setState({ templates })
  }

  onChangeCheckBox = e => {
    console.log(e.target.innerHTML) // TODO: is there a better way of doing this?
    this.props.addToModules(e.target.innerHTML)
    //this.props.templates.push()
  }

  onForumChangeCheckBox = (e, { checked }) => {
    this.props.updateHasForum(checked)
  }

  templateToRow = template => {
    console.log(template)
    return (
      <Grid.Column width={8} className="template-item" onClick={this.activateTemplate.bind(this, template.id)} style={{marginTop: 10}}>
          {/*
        <Image src='/images/wireframe/paragraph.png' />
        */
          }
        {template.name}
      </Grid.Column>
    )
  }

  render() {
    return (
      <div>
        <h5 className="ui header">Templates</h5>
        <Grid>
          <Grid.Row>
            {this.state.templates.map(this.templateToRow)}
          </Grid.Row>
        </Grid>

        <h5 className="ui header">Or Choose manually</h5>
        <Grid>
          <Grid.Row>
            <Grid.Column width={8}>
              <Checkbox label='Sumarios' onChange={this.onChangeCheckBox} />
            </Grid.Column>
            <Grid.Column width={8}>
              <Checkbox label='Recursos' onChange={this.onChangeCheckBox} />
            </Grid.Column>
            <Grid.Column width={8}>
              <Checkbox label='Bibliografia' onChange={this.onChangeCheckBox} />
            </Grid.Column>
          </Grid.Row>

          <Grid.Row>
            <Grid.Column width={8}>
              <Checkbox label='Anuncios' onChange={this.onChangeCheckBox} />
            </Grid.Column>
            <Grid.Column width={8}>
              <Checkbox label='Forum' onChange={this.onForumChangeCheckBox} />
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </div>
    )
  }

  //TODO: Do I need to make all this code to have clickable templates...I need to manage this functionality
  //TODO: or does it exist already made?


  //TODO: change this to Toggle button using Semantic UI!!!!

  // No need for arrow function since Function.bind is being used.
  activateTemplate(templateId, e) {
    const activeElem = document.querySelector('.template-item-active')

    // Deactivate the current template.
    if (activeElem) {
      activeElem.classList.remove('template-item-active')
      templateId = null
    }
    if (e.target !== activeElem) { // then it means the user choose another template
      e.target.classList.remove('template-item-active:hover')
      e.target.classList.add('template-item-active')
    }

    console.log(templateId)
    this.props.activateTemplate(templateId) // update parent's state.
  }

  updatePropsTemplates() {
    //TODO: is this valid duplicated code (redundant)?
    const activeElem = document.querySelector('.template-item-active')
    /*if (activeElem)
      this.templates[activeElem.innerHTML]
        .forEach(templateName => this.props.modules.push(templateName))
    else
      // TODO: is there a better way of getting all checked boxes ?
      document.querySelectorAll(".ui.checkbox.checked label")
        .forEach(label => this.props.modules.push(label.innerHTML))*/
  }
}
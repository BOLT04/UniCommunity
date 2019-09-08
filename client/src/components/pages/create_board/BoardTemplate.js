import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Grid, Checkbox } from 'semantic-ui-react'

import './css/BoardTemplate.css'

import asyncCollectionRspToList from '../../../service/mapper/collectionJson-mapper'
import { COLLECTION_JSON } from '../../../common/constants'
import { withUtilsConsumer } from '../../withUtilsConsumer'
import { rels } from '../../../common/rels-registery'

class BoardTemplate extends Component {
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

  async componentDidMount() {
    const home = sessionStorage.getObject('home')
    const href = home[rels.templates]
    //TODO: test with href and TAKE OUT '/templates'
    const rsp = await this.props.utilsObj.asyncRelativeFetch('/templates', COLLECTION_JSON)
    const templates = (await asyncCollectionRspToList(rsp)).items
    
    this.setState({ templates })
  }

  onChangeCheckBox = e => {
    e.preventDefault()

    this.props.addToModules(e.target.innerHTML)
  }

  onForumChangeCheckBox = (e, { checked }) => {
    e.preventDefault()
    this.props.updateHasForum(checked)
  }

  templateToRow = template => (
    <Grid.Column
      key={template.id}
      width={8}
      className='template-item'
      onClick={this.activateTemplate.bind(this, template.id)}
      style={{marginTop: 10}}
    >
      {template.name}
    </Grid.Column>
  )

  render = () => (
    <div>
      <h5 className='ui header'>Templates</h5>
      <Grid>
        <Grid.Row>
          {this.state.templates.map(this.templateToRow)}
        </Grid.Row>
      </Grid>

      <h5 className='ui header'>Or Choose manually</h5>
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

    this.props.activateTemplate(templateId) // update parent's state.
  }
}

export default withUtilsConsumer(BoardTemplate)
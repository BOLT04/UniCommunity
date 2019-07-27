import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Input, Form, Checkbox, Button } from 'semantic-ui-react'

import CreateBoardApi from '../../service/CreateBoardApi'

export default class FileUpload extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi),
  }


  render() {
    return (
      <Form onSubmit={this.onFormSubmit}>
        <Form.Field>
          <Button
            content='Choose File'
            labelPosition='left'
            icon='file'
            onClick={() => this.fileInputRef.current.click()}
          />
          <input
            ref={this.fileInputRef}
            type='file'
            hidden
            onChange={this.fileChange}
          />
        </Form.Field>
        <Button type='submit'>Upload</Button>
      </Form>
    )
  }
}

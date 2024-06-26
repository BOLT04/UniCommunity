import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Form, Checkbox } from 'semantic-ui-react'
 
import { createForumPostsAsync } from '../../../service/ForumApi'
import rspToBoardAsync from '../../../service/mapper/board-mapper'

import { APPLICATION_HAL_JSON } from '../../../common/constants'
import { asyncRelativeFetch, removeFunctionsFrom } from '../../../common/common'
import routes from '../../../common/routes'
import { rels } from '../../../common/rels-registery'

import CreateBoardApi from '../../../service/CreateBoardApi'
import CreateModuleItem from './CreateModuleItem'

export default class CreatePost extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi),
  }

  isAnonymous = false

  onChangeCheckBox = (e, { checked }) => {
    e.preventDefault()
    this.isAnonymous = checked
  }

  // An arrow function is used because this function is used in an onClick prop, meaning there 
  // is no need to use Function::bind() to capture "this".
  submitCreatePostHandler = async (title, content) => {
    const url = this.props.location.state.createPostUrl

    const rsp = await createForumPostsAsync(url, title, content, this.isAnonymous)
    const rspObj = await rsp.json()

    // Check if we can redirect to the Board page.
    if (rspObj._links) {
      const boardUrl = rspObj._links[rels.getBoard]
      if (boardUrl) {
        const boardRsp = await asyncRelativeFetch(boardUrl.href, APPLICATION_HAL_JSON)
        let board = await rspToBoardAsync(boardRsp)
            
        // We need to remove any functions because history.push doesn't support that state has functions
        board = removeFunctionsFrom(board)
        this.props.history.push(routes.getBoardUri(board.id), { board })
      }
    }
  }

  render() {
    return (     
      <CreateModuleItem 
        header='Create a Post'
        submitOnClick={this.submitCreatePostHandler}>
        
        <Form.Field>
          <Checkbox label='Send Anonimously' onChange={this.onChangeCheckBox} />
        </Form.Field>
      </CreateModuleItem>
    )
  }
}

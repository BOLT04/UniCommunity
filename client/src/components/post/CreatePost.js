import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Input, Form, Checkbox, Button } from 'semantic-ui-react'
 
import { createForumPostsAsync } from '../../api/ForumApiImpl'
import rspToBoardAsync from '../../api/mapper/board-mapper'

import { APPLICATION_HAL_JSON } from '../../common/constants'
import { asyncRelativeFetch } from '../../common/common'
import routes from '../../common/routes'
import { rels } from '../../common/rels-registery'

import CreateBoardApi from '../../api/CreateBoardApi'
import CreateModuleItem from './CreateModuleItem'

export default class CreatePost extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi),
  }

  isAnonymous = false// TODO: use this on the response

  onChangeCheckBox = (e, { checked }) => {
    e.preventDefault()
    this.isAnonymous = checked
  }

  // An arrow function is used because this function is used in an onClick prop, meaning there 
  // is no need to use Function::bind() to capture "this".
  submitCreatePostHandler = async (title, content) => {
    console.log(title)
    console.log(content)

    const url = this.props.location.state.createPostUrl

    const rsp = await createForumPostsAsync(url, title, content, this.isAnonymous)
    debugger
    const rspObj = await rsp.json()
debugger
    //TODO: move this code that handles hal+json and responses from the API to another place,
    //todo: in this class its only logic related to this component
    
    //TODO: the idea behind looking at these links is to know if we can navigate/redirect to the board view
    //todo: if the server says we can't (doesnt include the link on the response) then we cant.
    // Check if we can redirect to the Board page.
    if (rspObj._links) {
      debugger
      const boardUrl = rspObj._links[rels.getBoard]
      debugger
      if (boardUrl) {
        // TODO: I think this should be refactored and moved to BoardView. So that this component isn't responsible
        //for making requests to a board

        const boardRsp = await asyncRelativeFetch(boardUrl.href, APPLICATION_HAL_JSON)
        let board = await rspToBoardAsync(boardRsp)
    
        board.id = 1 // TODO: remove when this is in server impl.
    
        debugger
        // We need to remove any functions because history.push doesn't support that state has functions
        board = JSON.parse(JSON.stringify(board))
        debugger
        this.props.history.push(routes.getBoardUri(board.id), {board})
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

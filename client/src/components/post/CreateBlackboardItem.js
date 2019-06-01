import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Input, Form, Checkbox, Button } from 'semantic-ui-react'

import MdEditor from 'react-markdown-editor-lite'
 
import { createForumPostsAsync } from '../../api/ForumApiImpl'
import rspToBoardAsync from '../../api/mapper/board-mapper'

import routes from '../../common/routes'
import CreateBoardApi from '../../api/CreateBoardApi';
import CreateModuleItem from './CreateModuleItem';

export default class CreatePost extends Component {
  static propTypes = {
    api: PropTypes.instanceOf(CreateBoardApi).isRequired,
  }

  // An arrow function is used because this function is used in an onClick prop, meaning there 
  // is no need to use Function::bind() to capture "this".
  submitCreatePostHandler = async (title, content) => {
    console.log(title)
    console.log(content)

    const url = this.props.location.state.createPostUrl

    const rsp = await createForumPostsAsync(url, title, content, this.isAnonymous)
    const rspObj = await rsp.json()

    //TODO: move this code that handles hal+json and responses from the API to another place,
    //todo: in this class its only logic related to this component
    
    //TODO: the idea behind looking at these links is to know if we can navigate/redirect to the board view
    //todo: if the server says we can't (doesnt include the link on the response) then we cant.
    // Check if we can redirect to the Board page.
    if (rspObj._links) {
      const boardUrl = rspObj._links['http://localhost:8080/rels/getBoard']//todo: REMOVE HARDCODED
      if (boardUrl) {
        // TODO: I think this should be refactored and moved to BoardView. So that this component isn't responsible
        //for making requests to a board
        console.log(this.props.api)
        const rsp2 = await this.props.api.getBoardAsync(boardUrl.href)

        const board = await rspToBoardAsync(rsp2)
    
        board.id = 1 // TODO: remove when this is in server impl.
    
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

        {/*<FileUpload />*/}
      </CreateModuleItem>
    )
  }
}

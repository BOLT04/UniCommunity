import CreateBoardApi from './CreateBoardApi'
import { buildUri } from '../common/common'

import { asyncPostRequest } from '../common/common'

import config from '../unicommunity-config.json'
const baseUri = `http://${config.serverHost}:${config.serverPort}` //TODO: this is repeated in many places. Find a way to clean it.

export default class CreateBoardApiImpl extends CreateBoardApi {

  // TODO: implement using hal+forms
  //todo: for this this method receives 2 urls, one for the createBoard endpoint and another to get the hal forms
  //todo: specifyng if its a post, the headers, etc
  createBoardAsync = async (url, name, description, { templateId, blackboardNames, hasForum }) => {// todo remove repeated code in all mocks
    // Construct request body
    const body = {
      name,
      description
    }

    if (templateId != undefined)
      body.templateId = templateId
    else {// TODO: is there a way to do this cleaner in es6?
      body.blackboardNames = blackboardNames
      body.hasForum = hasForum
    }

    return await asyncPostRequest(url, body)//TODO: this await should be outside, when someone calls this fn
  }

  getBoardAsync = async (url) => {
    return fetch('http://localhost:8080' + url)//todo: REMOVE prefix
  }

  getAllBoardsAsync = async relativeUrl => fetch(buildUri(relativeUrl))

  getTemplatesAsync = async (url) => {
    return fetch(url)
  }
}

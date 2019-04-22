import CreateBoardApi from './CreateBoardApi'

export default class CreateBoardApiImpl extends CreateBoardApi {

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

    return await fetch(url, {
      method: 'post',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    })
  }

  getBoardAsync = async (url) => {
    return fetch('http://localhost:8080' + url)//todo: REMOVE prefix
  }

  getTemplatesAsync = async (url) => {
    return fetch(url)
  }
}

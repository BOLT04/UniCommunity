import CreateBoardApi from './CreateBoardApi'

export default class CreateBoardApiImpl extends CreateBoardApi {

  createBoardAsync = async (url, name, description, { templateId, moduleNames }) => {// todo remove repeated code in all mocks
    // Construct request body
    const body = {
      name,
      description
    }
    if (templateId != undefined)
      body.templateId = templateId
    else
      body.moduleNames = moduleNames

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

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
      body: JSON.stringify(body)
    })
  }

  getBoardAsync = async (url, id) => {
    return await fetch(url)
  }

  getTemplatesAsync = async () => {
    const rsp = new Response()
    rsp.json = () => templates
    rsp.headers.append('Content-Type', 'application/vnd.collection+json')

    return rsp
  }
}

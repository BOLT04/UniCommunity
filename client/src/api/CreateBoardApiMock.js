import CreateBoardApi from './CreateBoardApi'

const body = {
  name: "PDM",
  description: "a desc...",
  _links: {
    self: { href: '/boards/1' },
    '/rels/nav': { href: '/' },
    '/rels/blackboards': { href: '/boards/1/blackboards' },
    '/rels/forum': { href: '/boards/1/forum/posts' }
  },
  _embedded: {
    "http://localhost:8080/rels/blackboards": [
      {
        "name": "Sumários",
        "_links": {
          "self": { "href": "/boards/1/blackboards/1" }
        }
      },
      {
        "name": "Anúncios",
        "_links": {
          "self": { "href": "/boards/1/blackboards/2" }
        }
      },
      {
        "name": "Recursos",
        "_links": {
          "self": { "href": "/boards/1/blackboards/3" }
        }
      }
    ]
  }
}

//Format: {1: {boardObject}}
const boards = { }

export default class CreateBoardApiMock extends CreateBoardApi {

  boardId = 1

  createBoardAsync = async (name, description) => {// todo remove repeated code in all mocks
    const { boardId } = this
    const board = { 
      name, 
      description,
      _links: {
        self: { href: `/boards/${boardId}` },
        '/rels/nav': { href: '/' },
        '/rels/blackboards': { href: `/boards/${boardId}/blackboards` },
        '/rels/forum': { href: `/boards/${boardId}/forum/posts` }
      }
    }

    ++boardId
    
    const rsp = new Response()
    rsp.json = () => board
    rsp.headers.append('Content-Type', 'application/hal+json')
    
    return rsp
  }

  /*async createBoardAsync() {//TODO: why dis no work...its the same as above😵😵😵😵
    const rsp = new Response()
    console.log('ooopa')
    rsp.json = () => body
    rsp.headers.append('Content-Type', 'application/hal+json')

    console.log('ooo')
    return rsp
  }*/

  getBoardAsync = async (id) => {//TODO: implement mock
    const body = boards[id]
    const rsp = new Response()
    rsp.json = () => body
    rsp.headers.append('Content-Type', 'application/hal+json')

    return rsp
  }
}

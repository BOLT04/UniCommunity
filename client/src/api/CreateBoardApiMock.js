import CreateBoardApi from './CreateBoardApi'

const templates = [
  {
      "name": "Template 1",
      "id": "1",
      "hasForum": true,
      "blackboardNames": [
        "Anuncios", "Sumarios", "Enunciado"
      ]
  },
  {
    "name": "Template 2",
    "id": "2",
    "hasForum": false,
    "blackboardNames": [
      "Anuncios"
    ]
  },
  {
    "name": "Template 3",
    "id": "3",
    "hasForum": true
  }
]


/*
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
}*/

//Format: {1: {boardObject}}
const boards = { }

export default class CreateBoardApiMock extends CreateBoardApi {

  boardId = 1

  createBoardAsync = async (url, name, description, { templateId, moduleNames }) => {// todo remove repeated code in all mocks
    console.log(templateId)
    console.log(moduleNames)
    const { boardId } = this
    const board = { 
      name, 
      description,
      _links: {
        self: { href: `/boards/${boardId}` },
        '/rels/nav': { href: '/' },
        '/rels/blackboards': { href: `/boards/${boardId}/blackboards` },
        '/rels/forum': { href: `/boards/${boardId}/forum/posts` }
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

    console.log(boards)
    boards[url] = board
    ++this.boardId
    console.log(boards)
    
    const rsp = new Response()
    rsp.json = () => board
    rsp.headers.append('Content-Type', 'application/hal+json')
    
    return rsp
  }

  getBoardAsync = async (url) => {
    console.log(boards)
    
    const body = boards[url]
    console.log(body)
    const rsp = new Response()
    rsp.json = () => body
    rsp.headers.append('Content-Type', 'application/hal+json')

    return rsp
  }

  getTemplatesAsync = async (url) => {
    const rsp = new Response()
    rsp.json = () => templates
    rsp.headers.append('Content-Type', 'application/json')

    return rsp
  }
}

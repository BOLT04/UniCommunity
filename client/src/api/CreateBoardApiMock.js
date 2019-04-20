import CreateBoardApi from './CreateBoardApi'

const templates = {
  "collection": {
      "version" : "1.0",
      "href" : "http://localhost:3000/templates",
      "links" : [
          {"rel" : "self", "href" : "http://localhost:3000/templates"},
          {"rel" : "/rels/nav", "href" : "http://localhost:3000/"},
          {"rel" : "/rels/home", "href" : "http://localhost:3000/home"}
      ],
      "items" : [
          {
              "href" : "http://localhost:3000/templates/1",
              "data" : [
                  {"name" : "name", "value" : "Template 1", "prompt" : "Template name"},
                  {"name" : "id", "value" : "1", "prompt" : "Template id"},
                  {"name" : "hasForum", "value" : "true"},
                  {"name" : "blackboardNames", "value" : "Anuncios,Sumarios,Enunciado"}
              ]
          },
          {
              "href" : "http://localhost:3000/templates/2",
              "data" : [
                  {"name" : "name", "value" : "Template 2", "prompt" : "Template name"},
                  {"name" : "id", "value" : "2", "prompt" : "Template id"},
                  {"name" : "hasForum", "value" : "false"},
                  {"name" : "blackboardNames", "value" : "Anuncios"}
              ]
          },
          {
            "href" : "http://localhost:3000/templates/2",
            "data" : [
                {"name" : "name", "value" : "Template 3", "prompt" : "Template name"},
                {"name" : "id", "value" : "2", "prompt" : "Template id"},
                {"name" : "hasForum", "value" : "false"},
                {"name" : "blackboardNames", "value" : "Anuncios"}
            ]
          },
          {
            "href" : "http://localhost:3000/templates/2",
            "data" : [
                {"name" : "name", "value" : "Template 4", "prompt" : "Template name"},
                {"name" : "id", "value" : "2", "prompt" : "Template id"},
                {"name" : "hasForum", "value" : "false"},
                {"name" : "blackboardNames", "value" : "Anuncios"}
            ]
          }
      ]
  }
}

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

  createBoardAsync = async (name, description, { templateId, moduleNames }) => {// todo remove repeated code in all mocks
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

    ++this.boardId
    
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

  getBoardAsync = async (id) => {
    const body = boards[id]
    const rsp = new Response()
    rsp.json = () => body
    rsp.headers.append('Content-Type', 'application/hal+json')

    return rsp
  }

  getTemplatesAsync = async () => {
    const rsp = new Response()
    rsp.json = () => templates
    rsp.headers.append('Content-Type', 'application/vnd.collection+json')

    return rsp
  }
}

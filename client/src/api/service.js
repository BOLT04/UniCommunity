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
/*
    modules: [
      {
        name: "Sumarios",
        content: [
          {
            name: "18/02/2019 - Course introduction",
            text: `* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)`
          },
          {
            name: "20/02/2019 - Designing Web APIs: Introduction",
            text: `* Web APIs (or HTTP APIs): Concept and Motivation\n* The [Architecture of the World Wide Web](https://www.w3.org/TR/webarch/)\n* The HTTP protocol: Introduction\n* Documentation:\n  * ["Introduction to Web APIs"](https://github.com/isel-leic-daw/1819v-public/wiki/Web-APIs)\n  * ["Designing evolvable Web APIs: Chapter 1"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch01.html)`
          }
        ]
      },
      {
        name: "Sumarios",
        content: [
          {
            name: "18/02/2019 - Course introduction",
            text: `* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)
            `
          }
        ]
      }
    ]
    */
  }

export default class CreateBoardApiMock extends CreateBoardApi {

    createBoardAsync = async () => {// todo remove repeated code in all mocks
        const rsp = new Response()
        rsp.json = () => body
        rsp.headers.append('Content-Type', 'application/hal+json')
        
        return rsp
    }
}

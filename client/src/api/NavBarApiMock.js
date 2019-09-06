import NavBarApi from './NavBarApi'

export default class NavBarApiMock extends NavBarApi {

    fetchNavigationMenuAsync = async () => Promise.resolve({
        _links: {
            "self": { "href": "/" },
            "/rels/home": { "href": "/home" },
            "/rels/login": { "href": "/login" } ,
            "/rels/createBoard": { "href": "/boards" }
        }  
    })
}

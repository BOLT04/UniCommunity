import NavBarApi from './NavBarApi'

export default class NavBarApiMock extends NavBarApi {

    fetchNavigationMenu = () => Promise.resolve({
        _links: {
            self: { href: "/" }, 
            signup: { href: "/signup" }, 
            login: { href: "/login" } 
        }  
    })
}

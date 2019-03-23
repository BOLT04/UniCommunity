import NavBarApi from './NavBarApi'

export default class NavBarApiMock extends NavBarApi{

    getNavigationMenu = () => Promise.resolve({
        _links: {
            self: { href: "/" }, 
            signup: { href: "/signup" }, 
            login: { href: "/login" } 
        }  
    })
}

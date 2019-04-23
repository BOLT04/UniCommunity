import NavBarApi from './NavBarApi'

export default class NavBarApiImpl extends NavBarApi {

    fetchNavigationMenuAsync = async (url) => fetch(url)
}

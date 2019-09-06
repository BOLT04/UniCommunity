import NavBarApi from './NavBarApi'

import config from '../unicommunity-config.json'
const baseUri = `http://${config.serverHost}:${config.serverPort}` //TODO: this is repeated in many places. Find a way to clean it.

export default class NavBarApiImpl extends NavBarApi {

    fetchNavigationMenuAsync = async url => fetch(`${baseUri}${url}`)
}

// The type Auth defined in this module is responsible for authentication services.
// It tracks the login status, so if the user is authenticated or not for example.
import { UserManager } from 'oidc-client'
import { buildUri } from '../common/common'
import { LogoutError } from '../common/errors'
import config from '../unicommunity-config.json'
import { baseClientUri } from '../common/common'

require('../common/storage-extensions')() //TODO: why is this needed if index.js already executes this code? 

const AUTH_TOKEN = 'authToken'
const USER = 'user'

const settings = {
    authority: config.authority,
    client_id: config.clientId,
	// This redirect URI needs to match one of the registered redirectUrls in the OpenID provider.
    redirect_uri: `${baseClientUri}/index.html`,
    popup_redirect_uri: `${baseClientUri}/index.html`,
    response_type: 'code',
    scope: 'openid email',
    loadUserInfo: true
}

export const userManager = new UserManager(settings)

function Auth() {
    let authenticated = false

    // Add a getter to user, so that it fetches the object from local storage when not available already.
    Object.defineProperty(this, USER, {
        get: () => {
            return localStorage.getObject(USER)
        }
    })

    /**
     * This function returns an object that represent the user from oidc-client library.
     */
    this.asyncLogin = async () => {
        let user = await userManager.getUser()
        if (!user)
            user = await userManager.signinPopup()

        localStorage.setItem(AUTH_TOKEN, user.access_token)
        localStorage.setObject(USER, user)
        authenticated = true
        return true
    }

    /**
     * Clears any authentication information stored on the browser. If the user is not authenticated then
     * throws an LogoutError.
     * @see LogoutError
     */
    this.logout = () => {
        if (!this.isAuthenticated())
            throw new LogoutError('Error: Attempted to logout without being authenticated first!')
        
        // Clear authentication tokens and other items in storage.
        sessionStorage.clear()
        localStorage.clear()
    
        authenticated = false
        return true
    }
    
    this.isAuthenticated = () => authenticated || localStorage.getItem(AUTH_TOKEN) !== null
}

export default new Auth()
// The type Auth defined in this module is responsible for authentication services.
// It tracks the login status, so if the user is authenticated or not for example.
import { UserManager } from 'oidc-client'
import { LogoutError } from '../common/errors'
import config from '../unicommunity-config.json'
import { baseClientUri, buildUri } from '../common/common'

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
     * This function returns an object with links that represent where the client can navigate after a
     * successful authentication. If null is returned than it means an error occured.
     */
    this.asyncLogin = async (relativeUrl, email, password) => {
        const body = { email, password }

        try {
            const rsp = await fetch(buildUri(relativeUrl), {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            })

            if (!rsp.ok) throw rsp

            const json = await rsp.json()
            const user = {
                email,
                name: json.name
            }
        
            this.token = new Buffer(`${email}:${password}`).toString('base64')
            localStorage.setItem(AUTH_TOKEN, this.token)
            localStorage.setObject(USER, user)
            authenticated = true

            return json._links
        } catch (e) {
            authenticated = false
            throw e
        }
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
    }
    
    this.isAuthenticated = () => authenticated || localStorage.getItem(AUTH_TOKEN) !== null
}

export default new Auth()
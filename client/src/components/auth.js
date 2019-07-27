// The type Auth defined in this module is responsible for authentication services.
// It tracks the login status, so if the user is authenticated or not for example.

import { buildUri } from '../common/common'
import { LogoutError } from '../common/errors'

function Auth() {
    let authenticated = false

    // Add a getter to user, so that it fetches the object from local storage when not available already.
    Object.defineProperty(this, 'user', {
        get: () => {
            debugger
            return localStorage.getObject('user')
        }
    })

    //TODO: use hal+forms!
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
        
            this.token = new Buffer(email +':'+ password).toString('base64')
            localStorage.setItem('authToken', this.token)
            localStorage.setObject('user', user)
            authenticated = true

            return json._links
        } catch (e) {
            authenticated = false
            throw e
        }
    }

    this.asyncLogout = async relativeUrl => {
        if (!authenticated)
            throw new LogoutError('Error: Attempted to logout without being authenticated first')
        //const rsp = await fetch(buildUri(relativeUrl))
        //todo: what is in the rsp from the API

        authenticated = false
        this.user = null

        return true
    }
    
    this.isAuthenticated = () => authenticated || localStorage.getItem('authToken') !== null
}

export default new Auth()
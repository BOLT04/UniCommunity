// The type Auth defined in this module is responsible for authentication services.
// It tracks the login status, so if the user is authenticated or not for example.

import { buildUri } from '../common/common'
import { LogoutError } from '../common/errors'

function Auth() {
    let authenticated = false

    //TODO: use hal+forms!
    /**
     * This function returns an object with links that represent where the client can navigate after a
     * successful authentication. If null is returned than it means an error occured.
     */
    this.asyncLogin = async (relativeUrl, email, password) => {
        // Construct request body
        const body = {
            email,
            password
        }

        const rsp = await fetch(buildUri(relativeUrl), {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
        const json = await rsp.json()

        this.user = {
            email: email,
            name: json.name
        }

        this.token = new Buffer(email +':'+ password).toString('base64')
        localStorage.setItem('authToken', this.token)
        localStorage.setItem('user', this.user)

        authenticated = true
        return json._links
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
    this.isAuthenticated = () => authenticated || localStorage.getItem('authToken')
}

export default new Auth()
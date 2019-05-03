// The type Auth defined in this module is responsible for authentication services.
// It tracks the login status, so if the user is authenticated or not for example.

import { buildUri } from '../common/common'

function Auth() {
    let authenticated = false

    this.login = async (relativeUrl, username, password) => {
        // Construct request body
        const body = {
            username,
            password
        }

        const rsp = await fetch(buildUri(relativeUrl), {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
        const bodh = await rsp.json()
        console.log({rsp, bodh})
        //todo: what is in the rsp from the API. It should be info about the user
        const user = {
            username: 'Dabe'
        }
        this.user = user

        const success = true //todo: change this later
        authenticated = true

        return success
    }

    this.logout = async relativeUrl => {
        const rsp = await fetch(buildUri(relativeUrl))
        //todo: what is in the rsp from the API

        authenticated = false
        this.user = null

        return true
    }
    this.isAuthenticated = () => authenticated
}

export default new Auth()
// The type Auth defined in this module is responsible for authentication services.
// It tracks the login status, so if the user is authenticated or not for example.

import { buildUri } from '../common/common'

function Auth() {
    let authenticated = false

    this.login = async relativeUrl => {
        const rsp = await fetch(buildUri(relativeUrl))
        //todo: what is in the rsp from the API

        const success = true //todo: change this later
        authenticated = true

        return success
    }

    this.logout = async relativeUrl => {
        const rsp = await fetch(buildUri(relativeUrl))
        //todo: what is in the rsp from the API

        authenticated = false

        return true
    }
    this.isAuthenticated = () => authenticated
}

export default new Auth()
export default class HomeApi {

    /**
     * Creates a new instance of HomeApiImpl. This is used to make a request and get the home of the API.
     * @param {string} baseUri - Base URI for the server HTTP API. Example: http://localhost:8080
     * @param {string} url     - Entry point path for the server HTTP API. Example: /home
     */
    constructor(baseUri, url) {
        this.homeUrl = `${baseUri}${url}`
    }

    fetchHomeAsync = async () => fetch(this.homeUrl)
}

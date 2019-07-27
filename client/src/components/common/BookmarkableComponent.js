import { Component } from 'react'

export default class BookmarkableComponent extends Component {
    /**
     * Add a property with the name serverHref to a component object, if the home resource object
     * containing all the navigation links is stored in session storage.
     * 
     * @param {string} rel - The property name of the home object located in storage, representing
     * a link relation.
     * @param {object} options - Represents the optional parameters. This object can contain the properties:
     * checkState    - bool, indicates whether or not we check if the serverHref is in this.props.location.state
     * parseTemplate - function with the descriptor (string) -> string, parses the template URL (the 
     * argument), returing an URL with the correct parameters.
     * multipleHref  - bool, indicates if the child component needs to manage various server URLs. Meaning that
     * the property serverHref needs to be of type array (string[]).
     * @returns {bool} - indicates if the property was added or if already present in the object.
     */
    addServerHrefOf(rel, { checkState, parseTemplate } = {}) {
        if (!this.serverHref) {
            // Check if the home object is already in storage
            const homeString = sessionStorage.getItem('home')
            if (homeString) {
                const home = JSON.parse(homeString)
                if (!checkState)
                    this.serverHref = home[rel]
                else { // Then check if the value is already provided by an outside component
                    const state = this.props.location.state
                    this.serverHref = state && state.serverHref || home[rel]
                }
debugger
                if (parseTemplate) this.serverHref = parseTemplate(this.serverHref)

                return true
            }

            return false
        }

        return true
    }

    addMultipleServerHref(argsArr, { checkState } = {}) {
        if (!this.serverHref) {
            // Check if the home object is already in storage
            const homeString = sessionStorage.getItem('home')
            if (homeString) {
                const home = JSON.parse(homeString)
                // Auxiliary function
                const mapRelsToHrefs = () => argsArr.map(arg =>
                    arg.parseTemplate
                        ? arg.parseTemplate(home[arg.rel])
                        : home[arg.rel]
                )

                if (!checkState)
                    this.serverHref = mapRelsToHrefs()
                else { // Then check if the value is already provided by an outside component
                    const state = this.props.location.state
                    this.serverHref = state && state.serverHrefArr || mapRelsToHrefs()
                }

                return true
            }

            return false
        }

        return true
    }
}
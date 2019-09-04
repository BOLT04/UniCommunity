import React, { Component } from 'react'

/**
 * Handles errors (e.g. exceptions that occur while mapping the response payload to a model representation) that
 * occur in other components.
 */
export default class ErrorHandler extends Component {
    state = {
        showError: false
    }

    handleError = error => this.setState({ error })

    /*render = () => this.state.showError
        ? <ErrorMessagePresenter 
            error={this.props.error}
            closeOnClickHandler={this.closeOnClickHandler} />
        : null*/

    render() {
        return (
            this.children(this.handleError)
        )
    }
}


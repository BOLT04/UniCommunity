import React, { Component } from 'react'

export default class ErrorMessage extends Component {
    state = {
        showError: false
    }

    closeOnClickHandler = e => {
        this.setState({ showError: true })
    }

    render = () => this.state.showError
        ? <ErrorMessagePresenter 
            error={this.props.error}
            closeOnClickHandler={this.closeOnClickHandler} />
        : null
}


const ErrorMessagePresenter = ({ error, closeOnClickHandler }) => (
    <div className='ui negative message'>
        <i className='close icon' onClick={closeOnClickHandler} />
        <div className='header'>
            {error.message}
        </div>
    </div>
)
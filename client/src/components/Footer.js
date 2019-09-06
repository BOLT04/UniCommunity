'use strict'
import React, { Component } from 'react'

import { Icon } from 'semantic-ui-react'

export default class Footer extends Component {
    render() {
        return (
            <div className="ui inverted vertical footer segment">
                <div className="ui inverted center aligned segment">
                    <Icon name='copyright' /> 2018-2019 UniCommunity
                </div>
                <div className="ui container">
                    <div className="ui stackable inverted divided equal height stackable grid">
                        <div className="three wide column">
                            <h4 className="ui inverted header">About</h4>
                            <p>
                                We are a group of students that made this application as our Final Year Project
                            </p>
                        </div>
                        <div className="three wide column">
                            <h4 className="ui inverted header">Services/References</h4>
                            <div className="ui inverted link list">
                                <a href="https://www.freepik.com/free-photos-vectors/logo">Logo vector created by freepik - www.freepik.com</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

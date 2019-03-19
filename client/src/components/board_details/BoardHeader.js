import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class BoardHeader extends Component {
    static propTypes = {
        board: PropTypes.object
    }

    render() {
        return (
            <div>
                <h4 className="ui blue header">{this.props.board.name}</h4>
                <div className="ui secondary pointing menu">
                    <a className="item active">
                        Home
                    </a>
                    <a className="item">
                        Messages
                    </a>
                    <a className="item">
                        Friends
                    </a>
                    <div className="right menu">
                        <a className="ui item">
                            Logout
                        </a>
                    </div>
                </div>
            </div>
        )
    }
}

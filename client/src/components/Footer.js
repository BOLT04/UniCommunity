import React, { Component } from 'react'

export default class Footer extends Component {
    render() {
        return (
            <div className="ui sticky inverted vertical footer segment">
                <div className="ui container">
                    <div className="ui stackable inverted divided equal height stackable grid">
                        <div className="three wide column">
                            <h4 className="ui inverted header">About</h4>
                            <div className="ui inverted link list">
                                <a href="#" className="item">Sitemap</a>
                                <a href="#" className="item">Contact Us</a>
                                <a href="#" className="item">Religious Ceremonies</a>
                                <a href="#" className="item">Gazebo Plans</a>
                            </div>
                        </div>
                        <div className="three wide column">
                            <h4 className="ui inverted header">Services/References</h4>
                            <div className="ui inverted link list">
                                <a href="https://www.freepik.com/free-photos-vectors/logo">Logo vector created by freepik - www.freepik.com</a>
                                <a href="#" className="item">Banana Pre-Order</a>
                                
                            </div>
                        </div>
                        <div className="seven wide column">
                            <h4 className="ui inverted header">Footer Header</h4>
                            <p>Extra space for a call to action inside the footer that could help re-engage users.</p>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
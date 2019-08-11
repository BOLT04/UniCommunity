import React, { Component } from 'react'

import { Button, Icon, Sticky } from 'semantic-ui-react'

import './css/BackToTopButton.css'

//TODO: This is being called a lot of times! Is it a good solution?
/*window.addEventListener('scroll', e => {
    document.getElementById('backToTopBtn')
        .classList.remove("hidden")
})*/

export default class BackToTopButton extends Component {

    handleClick = e => {
        if (e.target.children.length === 0) // Then the icon was clicked
            e.target.parentElement.classList.add('hidden')
        e.target.classList.add('hidden')
    }

    render() {
        return (
            <div onClick={this.handleClick}>
                <Sticky pushing>
                    <Button id='backToTopBtn' circular icon size='huge' floated='right' color='black'
                        attached='bottom'>
                        <Icon name='chevron up' />
                    </Button>
                </Sticky>
            </div>
        )
    }
}

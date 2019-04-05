import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Accordion, Icon, Transition } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

/**
 * This component is used to display the modules/blackboards of a Board, for example the Summary and
 * Announcements.
 */
export default class ModulesView extends Component {
    state = { activeIndex: 0, visible: true }

    static propTypes = {
        board: PropTypes.object
    }

    handleClick = (e, titleProps) => {
        const { index } = titleProps
        const { activeIndex } = this.state
        const newIndex = activeIndex === index ? -1 : index
    
        /*
        React may batch multiple setState() calls into a single update for performance.
        Because this.props and this.state may be updated asynchronously, we should not rely on their values 
        for calculating the next state!
        */
        this.setState(state => ({ 
            activeIndex: newIndex,
            visible: !state.visible
        }))
    }

    render() {
        const { activeIndex, visible } = this.state
        const { board } = this.props

        return (
            <div>
                <Accordion fluid styled>
                    <Accordion.Title active={activeIndex === 0} index={0} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        {board.modules[0].name}
                    </Accordion.Title>
                    <Transition visible={visible} animation='slide down' duration={500}>
                        <Accordion.Content active={activeIndex === 0}>
                            <h4>{board.modules[0].content[0].name}</h4>
                            <ReactMarkdown source={board.modules[0].content[0].text} />

                            <h4>{board.modules[0].content[1].name}</h4>
                            <ReactMarkdown source={board.modules[0].content[1].text} />
                        </Accordion.Content>
                    </Transition>

                    <Accordion.Title active={activeIndex === 1} index={1} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        What kinds of dogs are there?
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 1}>
                        <p>here...</p>
                    </Accordion.Content>
                </Accordion>
            </div>
        )
    }
}

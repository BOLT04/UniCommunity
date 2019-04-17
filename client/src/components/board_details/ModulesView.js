import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Accordion, Icon, Transition, Button } from 'semantic-ui-react'

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

    moduleToAccordion(module, index) {
        const { activeIndex, visible } = this.state
        const isActive = activeIndex === index

        return (
            <>
                <Accordion.Title active={isActive} index={index} onClick={this.handleClick}>
                    <Icon name='dropdown' />
                    {module.name}
                    <Button floated='right'>Create</Button>                
                </Accordion.Title>
                <Transition visible={visible} animation='slide down' duration={500}>
                    <Accordion.Content active={isActive}>
                        {module.content.map(item => (
                            <>
                                <h4>{item.name}</h4>
                                <ReactMarkdown source={item.text} />
                            </>
                        ))}

                    </Accordion.Content>
                </Transition>
            </>
        )
    }

    render() {
        const { activeIndex, visible } = this.state
        const { board } = this.props

        const activeMap = {
            0: 
        }

        return (
            <div>
                <Accordion fluid styled exclusive={false}>
                    {/*board.modules.map(moduleToAccordion)*/}
                    <Accordion.Title active={activeIndex === 0} index={0} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        {board.modules[0].name}
                        <Button floated='right'>Create</Button>
                        
                    </Accordion.Title>
                    <Transition visible={activeIndex === 0} animation='slide down' duration={500}>
                        <Accordion.Content active={activeIndex === 0}>
                            <h4>{board.modules[0].content[0].name}</h4>
                            <ReactMarkdown source={board.modules[0].content[0].text} />

                            <h4>{board.modules[0].content[1].name}</h4>
                            {board.modules[0].content[1].text}

                            <h4>{board.modules[0].content[2].name}</h4>
                            <ReactMarkdown source={board.modules[0].content[2].text} />
                        </Accordion.Content>
                    </Transition>

                    <Accordion.Title active={activeIndex === 1} index={1} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        What kinds of dogs are there?
                    </Accordion.Title>
                    <Transition visible={activeIndex === 1} animation='slide down' duration={500}>
                        <Accordion.Content active={activeIndex === 1}>
                            <p>here...</p>
                        </Accordion.Content>
                    </Transition>
                    
                </Accordion>
            </div>
        )
    }
}

import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Accordion, Icon, Transition } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

export default class ModulesView extends Component {
    state = { activeIndex: 0, visible: true }

    static propTypes = {
        board: PropTypes.object
    }

    handleClick = (e, titleProps) => {
        const { index } = titleProps
        const { activeIndex } = this.state
        const newIndex = activeIndex === index ? -1 : index
    
        this.setState({ 
            activeIndex: newIndex,
            visible: !this.state.visible
        })
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
                        <p>
                            There are many breeds of dogs. Each breed varies in size and temperament. Owners often
                            select a breed of dog that they find to be compatible with their own lifestyle and
                            desires from a companion.
          </p>
                    </Accordion.Content>

                    <Accordion.Title active={activeIndex === 2} index={2} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        How do you acquire a dog?
        </Accordion.Title>
                    <Accordion.Content active={activeIndex === 2}>
                        <p>
                            Three common ways for a prospective owner to acquire a dog is from pet shops, private
                            owners, or shelters.
          </p>
                        <p>
                            A pet shop may be the most convenient way to buy a dog. Buying a dog from a private
                            owner allows you to assess the pedigree and upbringing of your dog before choosing to
                            take it home. Lastly, finding your dog from a shelter, helps give a good home to a dog
                            who may not find one so readily.
          </p>
                    </Accordion.Content>
                </Accordion>
            </div>
        )
    }
}

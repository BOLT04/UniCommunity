'use strict'
import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Accordion, Icon, Transition, Header } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

import Forum from './Forum'
import { CreateModuleButton } from './CreateModuleButton'

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
//TODO: maybe the way to clean code is through a function contentSupp, since that is the main difference between
//todo: blackboards and forum
    /**
     * @param {Function} contentSupp??? - A funtion that returns the tree of React elements to be children of
     * Accordion.Content, given a.
     */
    blackboardToAccordion = (blackboard, index) => {
        const { activeIndex, visible } = this.state
        const isActive = activeIndex === index

        function decideContent() {
            return blackboard.name !== 'Forum'// TODO: remove the ternary since its always blackboards
                ? blackboard.items.map(item => (
                    <>
                        <Header as='h4' color='orange'>{item.name}</Header>
                        <hr />
                        <ReactMarkdown source={item.content} />
                    </>
                ))
                : <Forum />
        }
        const linkToObj = {
            pathname: blackboard.createLink.clientHref,
            state: { createBlackboardItemUrl: blackboard.createLink.serverHref }
        }

        return (
            <div key={index}>
                <Accordion.Title active={isActive} index={index} onClick={this.handleClick}>
                    <Icon name='dropdown' />
                    {blackboard.name}
                </Accordion.Title>

                <CreateModuleButton linkToObj={linkToObj} />

                <Transition visible={isActive} animation='slide down' duration={500}>
                    <Accordion.Content active={isActive}>
                        {decideContent()}

                    </Accordion.Content>
                </Transition>
            </div>
        )
    }

    //TODO: remove redundant code later.
    renderForum(board) {
        const { posts, createPostHrefObj } = board.modules.forum
        
        const { activeIndex } = this.state
        const index = board.modules.blackboards.length
        const isActive = activeIndex === index
        
        const linkToObj = {
            pathname: createPostHrefObj.clientHref,
            state: { createPostUrl: createPostHrefObj.serverHref }
        }

        return (
            <div key={index}>
                <Accordion.Title active={isActive} index={index} onClick={this.handleClick}>
                    <Icon name='dropdown' />
                    Forum
                </Accordion.Title>

                <CreateModuleButton linkToObj={linkToObj} />

                <Transition visible={isActive} animation='slide down' duration={500}>
                    <Accordion.Content active={isActive}>
                        <Forum board={board} posts={posts}/>
                    </Accordion.Content>
                </Transition>
            </div>
        )
    }

    render() {
        const { board } = this.props
        console.log(board)
        
        return board.modules 
                    ? 
                        <Accordion fluid styled exclusive={false}>
                            {board.modules.blackboards &&
                                board.modules.blackboards.map(this.blackboardToAccordion)}

                            {board.modules.forum && this.renderForum(board) }
                        </Accordion>
                    : null
    }
}

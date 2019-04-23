import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Link } from "react-router-dom"

import { Accordion, Icon, Transition, Button } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

import Forum from './Forum'

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
            return blackboard.name !== 'Forum'
                ? blackboard.items.map(item => (
                    <>
                        <h4>{item.name}</h4>
                        <ReactMarkdown source={item.content} />
                    </>
                ))
                : <Forum />
        }
        // to="/post"
        return (
            <div key={index}>
                <Accordion.Title active={isActive} index={index} onClick={this.handleClick}>
                    <Icon name='dropdown' />
                    {blackboard.name}
                </Accordion.Title>

{/* <Link 
                    to={{
                        pathname: module.createLink.clientHref,
                        state: {board: this.props.board}
                    }}> */}

                <Link to={blackboard.createLink.clientHref}>
                    <Button primary icon floated='right' labelPosition='right' style={{ marginTop: -35 }} >
                        <Icon name='plus' />
                        Create new
                    </Button>
                </Link>

                <Transition visible={isActive} animation='slide down' duration={500}>
                    <Accordion.Content active={isActive}>
                        {decideContent()}

                    </Accordion.Content>
                </Transition>
            </div>
        )
    }

    //TODO: remove redundant code later.
    renderForum({ posts, createPostHrefObj }, index) {
        const { activeIndex } = this.state
        const isActive = activeIndex === index

        return (
            <div key={index}>
                <Accordion.Title active={isActive} index={index} onClick={this.handleClick}>
                    <Icon name='dropdown' />
                    Forum
                </Accordion.Title>

                <Link 
                    to={{
                        pathname: createPostHrefObj.clientHref,
                        state: {createPostUrl: createPostHrefObj.serverHref}
                    }}
                >
                    <Button primary icon floated='right' labelPosition='right' style={{ marginTop: -35 }} >
                        <Icon name='plus' />
                        Create new
                    </Button>
                </Link>

                <Transition visible={isActive} animation='slide down' duration={500}>
                    <Accordion.Content active={isActive}>
                        <Forum posts={posts}/>
                    </Accordion.Content>
                </Transition>
            </div>
        )
    }

    render() {
        const { board } = this.props
        console.log(board)
        
        return (
            <>
                <Accordion fluid styled exclusive={false}>
                    {board.modules.blackboards.map(this.blackboardToAccordion)}

                    {board.modules.forum != undefined &&
                        this.renderForum(board.modules.forum, board.modules.blackboards.length)
                    }
                </Accordion>
            </>
        )
    }
}

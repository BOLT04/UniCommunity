import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Accordion, Icon, Transition, Header, Message } from 'semantic-ui-react'

import Markdown from 'react-markdown'

import Forum from './Forum'
import { CreateModuleButton } from './CreateModuleButton'
import EditModuleButton from './EditModuleButton'

import { withUtilsConsumer } from '../../withUtilsConsumer'

/**
 * This component is used to display the modules of a Board, for example the Summary or Announcements.
 */
class ModulesView extends Component {
    static propTypes = {
        board: PropTypes.object
    }

    constructor(props) {
        super(props)

        this.state = {
            activeIndex: 0,
            visible: true,
            infoMsgVisible: true,
            board: props.board
        }
    }
    
    handleClick = (e, titleProps) => {
        const { index } = titleProps
        const { activeIndex } = this.state
        const newIndex = activeIndex === index ? -1 : index

        this.setState(state => ({
            activeIndex: newIndex,
            visible: !state.visible
        }))
    }

    blackboardToAccordion = (blackboard, index) => {
        const { activeIndex } = this.state
        const isActive = activeIndex === index

        const updateBlackboard = updatedBlackboard =>
            this.setState(state => {
                state.board.modules.blackboards[index] = updatedBlackboard
                return state
            })
       
        const { board } = this.state

        return (
            <div key={index}>
                <Accordion.Title active={isActive} index={index} onClick={this.handleClick}>
                    <Icon name='dropdown' />
                    {blackboard.name}
                </Accordion.Title>

                { blackboard.editLinkHref &&
                    <EditModuleButton
                        board={board}
                        blackboard={blackboard}
                        updateBlackboard={updateBlackboard} />
                }

                { blackboard.createLink &&
                    <CreateModuleButton linkToObj={{
                        pathname: blackboard.createLink.clientHref,
                        state: { 
                            createBlackboardItemUrl: blackboard.createLink.serverHref,
                            boardId: board.id
                        }
                    }} />
                }

                <Transition visible={isActive} animation='slide down' duration={500}>
                    <Accordion.Content active={isActive}>
                        { blackboard.items.map(item => (
                            <>
                                <Header as='h4' color='orange'>{item.name}</Header>
                                <hr />
                                <Markdown source={item.content} />
                            </>
                        ))}

                    </Accordion.Content>
                </Transition>
            </div>
        )
    }

    renderForum(board) {
        const { posts, createPostHrefObj } = board.modules.forum
        
        // If the blackboards array is defined then the index of the Forum is the array length. If not its 0
        const auxArr = board.modules.blackboards
        const index = (auxArr && auxArr.length) || 0
        const { activeIndex } = this.state
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

    handleDismiss = () => {
        this.setState({ infoMsgVisible: false })
    }    

    render() {
        const { board, infoMsgVisible } = this.state

        return board.modules 
                    ? 
                        <Accordion fluid styled exclusive={false}>
                            { board.modules.blackboards
                                ? board.modules.blackboards.map(this.blackboardToAccordion)
                                : infoMsgVisible && 
                                    <Message
                                        info
                                        onDismiss={this.handleDismiss}
                                        header='Blackboards information not available' />
                            }

                            {board.modules.forum && this.renderForum(board) }
                        </Accordion>
                    : null
    }
}

export default withUtilsConsumer(ModulesView)
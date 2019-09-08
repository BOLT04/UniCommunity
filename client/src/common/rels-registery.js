// This module contains an object (relsRegistery) that maps the known custom link relations to whatever
// information is considered important for a given link relation (rel). 
// It can be the corresponding client URL, strings containing the CSS classes to be used as props to
// React elements or HTML elements, etc.
import React from 'react'
import UserProfileNavItem from '../components/navbar/UserProfileNavItem'

import routes from './routes'

export const rels = {
    getForumItems: '/rels/getForumItems',
    getForum: '/rels/getForum',
    createBlackboardItem: '/rels/createBlackboardItem',
    editBlackboard: '/rels/editBlackboard',
    createForumItem: '/rels/createForumItem',
    
    createComment: '/rels/createComment',
    getComments: '/rels/getComments',

    myBoards: '/rels/myBoards',
    getBoards: '/rels/getBoards',
    getBoard: '/rels/getBoard',
    editBoard: '/rels/editBoard',
    addMemberToBoard: '/rels/subscribe',
    removeMemberToBoard: '/rels/unsubscribe',

    createBoard: '/rels/createBoard',
    login: '/rels/login',
    getBlackboards: '/rels/getBlackboards',
    getBlackboardItems: '/rels/getBlackboardItems',
    nav: '/rels/nav',
    feed: '/rels/feed',
    userProfile: '/rels/userProfile',
    templates: '/rels/templates',
}

// Properties that are used on the context of a Navbar:
// toDisplayOnRight; name

const relsRegistery = {
    [rels.getForumItems]: {
        clientHref: '/forum',
        serverHref: null
    },
    [rels.createBlackboardItem]: {
        clientHref(board, blackboard) {
            return routes.getNewBlackboardItemUri(board, blackboard)
        },
        serverHref: null
    },
    [rels.createForumItem]: {
        clientHref: '/posts/new'
    },
    [rels.createComment]: {
        propName: 'createComment'
    },
    [rels.getComments]: {
        propName: 'getComments'
    },
    [rels.getBoards]: {
        clientHref: '/boards',
        name: 'All Boards'
    },
    [rels.createBoard]: {
        clientHref: '/boards/new',
        name: 'Create Board'
    },
    [rels.login]: {
        clientHref: '/login',
        name: 'Log in',
        class: 'ui primary basic button',
        toDisplayOnRight: true
    },
    [rels.getBlackboards]: {
    },
    // The property 'propName' is used to specify the property's name of the object in each component's state
    // For example in App.js it will define state.home.navMenuUrl
    [rels.nav]: {
        propName: 'navMenuUrl'
    },
    [rels.feed]: {
        propName: 'feedUrl'
    },
    [rels.userProfile]: {
        clientHref(userId) {
            return routes.getUserProfileUri(userId)
        },
        name: 'User Profile',
        toDisplayOnRight: true,
        render(props) {
            return <UserProfileNavItem {...props} />
        }
    }
}

export default relsRegistery
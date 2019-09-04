// This module contains an object (relsRegistery) that maps the known custom link relations to whatever
// information is considered important for a given link relation (rel). 
// It can be the corresponding client URL, strings containing the CSS classes to be used as props to
// React elements or HTML elements, etc.
import React from 'react'
import UserProfileNavItem from '../components/UserProfileNavItem'

import routes from './routes'

export const rels = {
    getForumItems: '/rels/getForumItems',
    getForum: '/rels/getForum',
    createBlackboardItem: '/rels/createBlackboardItem',
    editBlackboard: '/rels/editBlackboard',
    createForumItem: '/rels/createForumItem',
    
    createComment: '/rels/createComment',
    getComments: '/rels/getComments',

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
    userProfile: '/rels/userProfile'
}

const relsRegistery = {
    [rels.getForumItems]: {
        clientHref: '/forum',//TODO: is this being used?
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
    // This empty object is just to signal the mapper functions that the client app knows these link relations.
    [rels.createComment]: {
        propName: 'createComment'
    },
    [rels.getComments]: {
        propName: 'getComments'
    },
    [rels.getBoards]: {//TODO: take out prefix localhost....
        clientHref: '/boards',
        name: 'All Boards'
    },
    [rels.createBoard]: {
        clientHref: '/boards/new',
        name: 'Create Board' // TODO: this property is unique to the rels belonging to navbar, so should they be separated in another object?
    },
    [rels.login]: {
        clientHref: '/login',
        name: 'Log in',
        class: 'ui primary basic button',
        toDisplayOnRight: true // TODO: this property is unique to the rels belonging to navbar, so should they be separated in another object?
    },
    [rels.getBlackboards]: {
        //TODO:
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
        clientHref: '/profile',
        name: 'User Profile',
        toDisplayOnRight: true,
        render(props) {
            return <UserProfileNavItem {...props} />
        }
    }
}

export default relsRegistery
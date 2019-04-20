//TODO: if this is how its done, meaning i only have representations for modules that I know, for example Forum,
//todo: then i can only render those modules => i cant render user created modules!
const relsRegistery = {
    '/rels/forum': {
        clientHref: '/forum',
        serverHref: null
    },
    '/rels/createBlackboardItem': {
        clientHref: '/blackboardItem/new',
        serverHref: null
    },
    '/rels/createPost': {
        clientHref: '/posts/new'
    },
    '/rels/createBoard': {
        clientHref: '/board/create',
        name: 'Create Board', // TODO: this property is unique to the rels belonging to navbar, so should they be separated in another object?
    },
    '/rels/login': {
        clientHref: '/login',
        name: 'Log in',
        class: 'ui primary basic button',
        toDisplayOnRight: true // TODO: this property is unique to the rels belonging to navbar, so should they be separated in another object?
    }
}

export default relsRegistery
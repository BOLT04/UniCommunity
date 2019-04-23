import config from '../unicommunity-config.json'
// TODO: this code is repeated in index.js. Is this that big of a problem?
const baseUri = `http://${config.serverHost}:${config.serverPort}`

//TODO: if this is how its done, meaning i only have representations for modules that I know, for example Forum,
//todo: then i can only render those modules => i cant render user created modules!
const relsRegistery = {
    '/rels/getForumItems': {
        clientHref: '/forum',//TODO: is this being used?
        serverHref: null
    },
    '/rels/createBlackboardItem': {
        clientHref: '/blackboardItem/new',
        serverHref: null
    },
    'http://localhost:8080/rels/createForumItem': {
        clientHref: '/posts/new'
    },
    [`${baseUri}/rels/createBoard`]: {
        clientHref: '/board/create',
        name: 'Create Board', // TODO: this property is unique to the rels belonging to navbar, so should they be separated in another object?
    },
    '/rels/login': {
        clientHref: '/login',
        name: 'Log in',
        class: 'ui primary basic button',
        toDisplayOnRight: true // TODO: this property is unique to the rels belonging to navbar, so should they be separated in another object?
    },
    '/rels/getBlackboards': {
        //TODO:
    },
    // The property "propName" is used to specify the property's name of the object in each component's state
    // For example in App.js it will define state.home.navUrl
    [`${baseUri}/rels/nav`]: {
        propName: "navMenuUrl"
    },
    [`${baseUri}/rels/feed`]: {
        propName: "feedUrl"
    }
}

export default relsRegistery
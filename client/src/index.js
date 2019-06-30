import React from 'react'
import ReactDOM from 'react-dom'

import './index.css'
import 'semantic-ui-css/semantic.min.css'

import App from './components/App'
import HomeApiImpl from './api/HomeApiImpl'
import { asyncRelativeFetch } from './common/common'
import config from './unicommunity-config.json'


// The core Firebase JS SDK is always required
import * as firebase from 'firebase'

firebase.initializeApp(config.firebase)

const messaging = firebase.messaging()

messaging.requestPermission()
.then(() => {
    console.log('has permission')
    return messaging.getToken()
})
.then(console.log)
.catch(e => console.log(`error: ${e.message}`))

/*;
(async () => {
    try {
        await messaging.requestPermission()
        const token = await messaging.getToken()
        console.log(token)
        
    } catch (e) {
        console.log(`error: ${e.message}`)
    }
})()
console.log(12)*/
messaging.usePublicVapidKey('BIOXb526ZIqXFesteNvXDtm39e2Y_hTnv-iQUlFzDmmtQ4WgnMROs3XlVfGBJjjlxkD42Fdxcd7twP7QvHgV23g')
messaging.onMessage(payload => {
    console.log(1)
    console.log({payload})
}, e => console.log({e}), c => console.log({c}))
console.log(13)

require('./common/storage-extensions')()

const baseUri = `http://${config.serverHost}:${config.serverPort}`

ReactDOM.render(
    <App
        baseUri={baseUri}
        asyncRelativeFetch={asyncRelativeFetch}
        api={new HomeApiImpl(baseUri, config.serverEntryPoint)} />, 
    document.getElementById('root'))
import React from 'react'
import ReactDOM from 'react-dom'

import './index.css'
import 'semantic-ui-css/semantic.min.css'//TODO: remove this since we are using semantic UI react

import App from './components/App'
import HomeApiImpl from './service/HomeApiImpl'
import { 
    asyncRelativeFetch,
    asyncRelativeHttpRequest,
    asyncHalFormsRequest,
} from './common/common'
import asyncParseHalFormRsp from './service/mapper/halForms-mapper'

import config from './unicommunity-config.json'

import UtilsContext, { withUtilsConsumer } from './components/withUtilsConsumer'

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

require('./common/storage-extensions')()

const baseUri = `http://${config.serverHost}:${config.serverPort}`

const AppWithUtils = withUtilsConsumer(App)
const utilsObj = {
    asyncRelativeFetch,
    asyncRelativeHttpRequest,
    asyncHalFormsRequest,
    asyncParseHalFormRsp,
}
ReactDOM.render(
    <UtilsContext.Provider value={{
        utilsObj,
        firebase
    }}>
        <AppWithUtils
            baseUri={baseUri}
            api={new HomeApiImpl(baseUri, config.serverEntryPoint)} />
    </UtilsContext.Provider>,
    document.getElementById('root'))
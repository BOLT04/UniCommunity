import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import 'semantic-ui-css/semantic.min.css'

import App from './components/App'
import HomeApiImpl from './api/HomeApiImpl'
import { asyncRelativeFetch } from './common/common'
import config from './unicommunity-config.json'

const baseUri = `http://${config.serverHost}:${config.serverPort}`

ReactDOM.render(
    <App
        baseUri={baseUri}
        asyncRelativeFetch={asyncRelativeFetch}
        api={new HomeApiImpl(baseUri, config.serverEntryPoint)}/>, 
    document.getElementById('root'))

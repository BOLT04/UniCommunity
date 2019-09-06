import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import 'semantic-ui-css/semantic.min.css'

import App from './components/App'
import HomeApiImpl from './api/HomeApiImpl'
import config from './unicommunity-config.json'

const baseUri = `http://${config.serverHost}:${config.serverPort}`

ReactDOM.render(
    <App
        baseUri={baseUri}
        api={new HomeApiImpl(baseUri, config.serverEntryPoint)}/>, 
    document.getElementById('root'))

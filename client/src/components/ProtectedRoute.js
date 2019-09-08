import React from 'react'
import { Route, Redirect } from 'react-router-dom'

import auth from '../service/auth'

export default props => {
    const { component: Component, render, ...rest } = props

    const getComponent = props => {
        return !Component
            ? render(props)
            :  <Component {...props} />
    }

    return (
        <Route 
            {...rest} 
            render={props =>
                auth.isAuthenticated()
                    ? getComponent(props)
                    : <Redirect to='/' />
            }
        />
    )
}
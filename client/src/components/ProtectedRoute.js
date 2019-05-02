import React from 'react'
import { Route, Redirect } from 'react-router-dom'

import auth from 'auth'

export const ProtectedRoute = ({ component: Component, ...props }) => (
    <Route 
        {...props} 
        render={props =>
            auth.isAuthenticated()
                ? <Component {...props} />
                : <Redirect to='/' />
        }
    />
)
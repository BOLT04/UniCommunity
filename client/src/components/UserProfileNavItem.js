import React from 'react'

// Semantic UI imports
import { Image } from 'semantic-ui-react'

import auth from './auth'

const UserProfile = () => (
    <>
        <Image
            src='/img/default-profile.png'
            avatar
            circular />

        { auth.user && <span> {auth.user.name} </span> }
    </>
)
export default UserProfile
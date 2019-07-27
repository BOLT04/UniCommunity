import React from 'react'

// Semantic UI imports
import { Image } from 'semantic-ui-react'

import auth from '../service/auth'

const UserProfileNavItem = () => (
    <>
        <Image
            src='/img/default-profile.png'
            avatar
            circular />
        { auth.user && <span> {auth.user.name} </span> }
    </>
)

export default UserProfileNavItem
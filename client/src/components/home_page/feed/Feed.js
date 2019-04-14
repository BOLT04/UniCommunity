import React from 'react'

import { List } from 'semantic-ui-react'

import FeedItem from './FeedItem'

export default function Feed({ feedItemList }) {
    return (
        <List divided link verticalAlign='middle'>
            {feedItemList.map(feedItem => <FeedItem feedItem={feedItem}/>)}      
        </List>
    )
}

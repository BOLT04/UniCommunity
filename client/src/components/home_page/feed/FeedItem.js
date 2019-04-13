import React from 'react'
import { List, Image } from 'semantic-ui-react'

export default function Feed({ feedItem }) {
    
    function decideContent() {
        return feedItem.shortDesc !== undefined
            ?
                <List.Content>
                    <List.Header as='a' href={feedItem.feedItemHref}>
                        {feedItem.author !== undefined &&
                            <div>
                                Published by <strong>{feedItem.author}</strong>
                            </div>
                        } 
                        {' '}
                        {feedItem.name}
                    </List.Header>
                    <List.Description>
                        {feedItem.shortDesc}
                    </List.Description>
                </List.Content>
            :
                <List.Content>
                    <List.Header as='a'>{feedItem.name}</List.Header>
                </List.Content>
    }

    return (
        <List.Item>
            <Image avatar src='https://react.semantic-ui.com/images/avatar/small/rachel.png' />
            { decideContent() }    
      </List.Item>
    )
}

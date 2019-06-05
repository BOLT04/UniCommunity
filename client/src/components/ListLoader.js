import React from 'react'

import { Loader, Message } from 'semantic-ui-react'

/**
 * Renders a List of project items.
 * This component receives a render prop that specifies the element tree to render.
 */
export default ( { list, emptyListHeaderMsg, emptyListMsg, render }) =>
  !list
    ? <Loader />
    : list.length == 0
      ? renderMessage(emptyListHeaderMsg, emptyListMsg)
      : render()

const renderMessage = (emptyListHeaderMsg, emptyListMsg) => (
  <Message>
    <Message.Header>
      { emptyListHeaderMsg || 'No items available' }
    </Message.Header>
    <p>
      { emptyListMsg  || 'Consider creating items first.' }
    </p>
  </Message>
)
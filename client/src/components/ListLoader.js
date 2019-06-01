import React, { Component } from 'react'

import { Loader } from 'semantic-ui-react'

/**
 * Renders a List of project items.
 * This component receives a render prop that specifies the element tree to render.
 * 
 */
export default class ListLoader extends Component {

  render() {
    const { list, emptyListHeaderMsg, emptyListMsg, render } = this.props

    return !list
              ? <Loader />
              : list.length == 0
                ?
                  <div className='ui message'>
                    <div className='header'>
                        { emptyListHeaderMsg || 'No items available' }
                    </div>
                    <p>{ emptyListMsg  || 'Consider creating items first.' }</p>
                  </div>
                : render()
  }
}

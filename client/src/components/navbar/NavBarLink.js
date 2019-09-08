import React, { Component } from 'react'

import { APPLICATION_HAL_JSON } from '../../common/constants'
import { Menu } from 'semantic-ui-react'

export default class NavBarLink extends Component {

    constructor(props) {
      super(props)
  
      // Check if there needs to be further actions to get the built href value.
      // For example with the user profile link, it is necessary to get an identifier of the user resource in order
      // to build the client href (since the client URLs are not the same as the server URLs to prevent coupling)!
      const { reg } = props
      this.isFunction = typeof reg.clientHref === 'function'
      const href = this.isFunction ? '' : reg.clientHref
  
      this.state = { href }
    }
  
    onClickHandler = (e, data) => {
      const { onClick, serverHref } = this.props
      const { href } = this.state
  
      onClick(e, data, href, serverHref, this.body)
    }
  
    async componentDidMount() {
      if (!this.isFunction) return
  
      const { reg, serverHref } = this.props
      const href = await this.buildHref(reg.clientHref, serverHref)
      this.setState({ href })
    }
  
    /**
     * @param {function} buildClientHref - Function that builds the client href. Descriptor (string) -> string.
     * The function takes the id necesary to build the complete href.
     * @param {string} serverHref - Server URL for the resource that we want to build a client URL. This resource needs
     * to provide an "id" property on the response payload, representing the resource identifier.
     */
    async buildHref(buildClientHref, serverHref) {
      const rsp = await this.props.utilsObj.asyncRelativeFetch(serverHref, APPLICATION_HAL_JSON)
      const body = await rsp.json()
      this.body = body // Save the response body to give to the component that will render a page for this resource
  
      return buildClientHref(body.id)
    }
  
    render() {
      const { reg, activeItem, className, serverHref } = this.props
      const { href } = this.state
      debugger
  
      return (
        <Menu.Item
          link={href !== undefined}
          href={href}
          className={className}
          key={reg.name}
          name={reg.name}
          active={activeItem === reg.name}
          onClick={this.onClickHandler}
        >
          {reg.render
            ? reg.render({ serverHref })
            : reg.name
          }
        </Menu.Item>
      )
    }
}
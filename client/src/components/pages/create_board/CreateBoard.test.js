import React from 'react'
import ReactDOM from 'react-dom'
import CreateBoard from './CreateBoard'

it('renders without crashing', () => {
  const div = document.createElement('div')
  ReactDOM.render(<CreateBoard />, div)
  ReactDOM.unmountComponentAtNode(div)
})

import React from 'react'
import ReactDOM from 'react-dom'
import { shallow } from 'enzyme'

import Header from './Header'

describe('Header component test', () => {
  it('renders without crashing', () => {
    const div = document.createElement('div')
    ReactDOM.render(<Header />, div)
    ReactDOM.unmountComponentAtNode(div)
  })

  it('displays header text based on props', () => {
    const header = shallow(
      <Header
        header='Test 1'
        className='testClass'
        content=''
      />
    )
    
    expect(header.find('.testClass').text()).toBe('Test 1')
  })

  it('renders ReactMarkdown component', () => {
    const header = shallow(
      <Header
        header='Test 1'
        className='testClass'
        content=''
        inMd
      />
    )
    
    expect(header.find('ReactMarkdown').length).toEqual(1)
  })

  it('doesn\'t render ReactMarkdown component', () => {
    const header = shallow(
      <Header
        header='Test 1'
        className='testClass'
        content=''
      />
    )
    
    expect(header.find('ReactMarkdown').length).toEqual(0)
  })
})

import React from 'react'
import { mount } from 'enzyme'
import ReactDOM from 'react-dom'
import { withUtilsConsumer } from '../../withUtilsConsumer'
import BoardView from './BoardView'
/*

jest.mock('./__mocks__/Context')

describe('BoardView', () => {

  beforeEach(() => {
    jest.resetModules()
  })


  // Takes the context data we want to test, or uses defaults
const getBoardViewWithUtils = (context = { utilsObj: { asyncRelativeFetch: () => {}}}) => {
  
  // Will then mock the LocalizeContext module being used in our LanguageSelector component
  jest.doMock('./LocalizeContext', () => {
    return {
      LocalizeContext: {
        Consumer: props => props.children(context)
      }
    }
  });
  
  // you need to re-require after calling jest.doMock.
  // return the updated LanguageSelector module that now includes the mocked context
  return require('./BoardView').BoardView
}

  it('renders without crashing', () => {
    const div = document.createElement('div')
    ReactDOM.render(<CreateBoard />, div)
    ReactDOM.unmountComponentAtNode(div)
  })
  
  it('renders board\'s info.', () => {
    // Arrange
    const NAME = 'PC'
    const DESCRIPTION = 'concurrent programming is awesome'
    const board = {
      name: NAME,
      description: DESCRIPTION
    }
    const location = {
      state: {
        board
      }
    }

    // Act
    //const BoardView = getBoardViewWithUtils()
    const wrapper = mount(<BoardView location={location} />)
    
    // Assert
    expect(wrapper.contains(NAME)).toBe(true)
    expect(wrapper.contains(DESCRIPTION)).toBe(true)
  })
})
*/
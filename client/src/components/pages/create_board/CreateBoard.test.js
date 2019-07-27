import React from 'react'
import ReactDOM from 'react-dom'
import CreateBoard from './CreateBoard'

it('renders without crashing', () => {
  const div = document.createElement('div')
  ReactDOM.render(<CreateBoard />, div)
  ReactDOM.unmountComponentAtNode(div)
})

/*
it('check the submitCreateBoardReq callback', () => {
  const onClick = jest.fn(),
        ButtonComponent = mount(<CreateBoard />).find('#submitBtn');
  DateInputComponent.simulate('change', { target: {value: moment('2018-01-22')} });
  expect(onChange).toHaveBeenCalledWith('22.01.2018');
})
*/
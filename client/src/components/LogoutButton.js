import React from 'react'

export default ({ onClickHandler }) => (
    <button
        className='negative ui right floated button'
        onClick={onClickHandler}
    >
        Logout
    </button>
)
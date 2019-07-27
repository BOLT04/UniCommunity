import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { rels } from '../../../common/rels-registery'

import rspToBoardAsync from '../../../service/mapper/board-mapper'
import { withUtilsConsumer } from '../../withUtilsConsumer'
import AbstractModal from './AbstractModal'

const propertiesConfig = {
    name: {
        type: 'text'
    },
    description: {
        type: 'text'
    }
}

class EditBoardButton extends Component {
    static propTypes = {
        board: PropTypes.object
    }
    
    render() {
        const { board, updateBoard } = this.props

        return (
            <AbstractModal
                rel={rels.editBoard}
                relativeUrl={board.editBoardHref}
                currentResource={board}
                propertiesConfig={propertiesConfig}
                asyncParseRspToModel={rspToBoardAsync}
                updateUi={updateBoard}
                triggerIcon='pencil alternate'
                modalHeader={'Edit the Board\'s information'}
                successHeader='Update Board Completed'
            />
        )
    }
}

export default withUtilsConsumer(EditBoardButton)
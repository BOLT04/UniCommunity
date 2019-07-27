import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Checkbox } from 'semantic-ui-react'

import Blackboard from './model/Blackboard'
import { rels } from '../../common/rels-registery'

import { parseOutputModelToBlackboard } from '../../api/mapper/board-mapper'
import { withUtilsConsumer } from '../../components/withUtilsConsumer'

import AbstractModal from './AbstractModal'

const propertiesConfig = {
    name: {
        type: 'text'
    },
    description: {
        type: 'text'
    },
    notificationLevel: {
        type: 'checkbox',
        render({ name, prompt }, onChangeCheckBox) {
            // It is easier to give the property name as an argument of the function: onChangeCheckBox,
            // instead of repeating the same prop on the Checkbox react component with the name as the value.
            const onChange = e => onChangeCheckBox(e, name)

            return (
                <>
                    <label>{prompt}</label>

                    <Checkbox 
                        data-value='1'
                        label='never receive notifications'
                        onChange={onChange} />
                    <Checkbox
                        data-value='2'
                        label='always receive notifications'
                        onChange={onChange} />
                    <Checkbox
                        data-value='3'
                        label='only receive notifications with IMPORTANT'
                        onChange={onChange} />
                </>
            )
        }
    }
}

/**
 * This component handles the state required to update/edit a module (e.g. Blackboard) and the logic for
 * making that HTTP request.
 */
class EditModuleButton extends Component {
    static propTypes = {
        blackboard: PropTypes.instanceOf(Blackboard),
        board: PropTypes.object,
    }

    asyncParseRspToModel = async rsp => {
        const body = await rsp.json()
        return parseOutputModelToBlackboard(this.props.board, body)
    }

    render() {
        const { blackboard, updateBlackboard } = this.props

        return (
            <AbstractModal
                rel={rels.editBlackboard}
                relativeUrl={blackboard.editLinkHref}
                currentResource={blackboard}
                propertiesConfig={propertiesConfig}
                asyncParseRspToModel={this.asyncParseRspToModel}
                updateUi={updateBlackboard}
                modalHeader='Configure the Blackboard settings'
                successHeader='Update Blackboard Settings Completed'
            />
        )
    }
}

export default withUtilsConsumer(EditModuleButton)
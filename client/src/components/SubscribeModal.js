import React, { Component } from 'react'
import { Button, Header, Icon, Modal } from 'semantic-ui-react'

import { withRouter } from 'react-router-dom'

import { APPLICATION_HAL_JSON } from '../common/constants'
import routes from '../common/routes'
import { removeFunctionsFrom } from '../common/common'
import { rels } from '../common/rels-registery'

import { withUtilsConsumer } from './withUtilsConsumer'

class SubscribeModal extends Component {
	
	constructor(props) {
		super(props)

		this.state = {}
	}

	yesOnClickHander = async e => {
		const { board } = this.props
		const url = board.getHrefOfRel(rels.addMemberToBoard)
		try {
			const rsp = await this.props.utilsObj.asyncRelativeFetch(url, APPLICATION_HAL_JSON)
			if (!rsp.ok) {}//TODO: handle error

			const redirectPath = routes.getBoardUri(board.id)
			this.props.history.push(redirectPath, { board: removeFunctionsFrom(board) })
		} catch(e) {
			//TODO: handle error
		}
	}

	render() {
		const { board } = this.props

		return (
			<Modal trigger={
				<Button primary>
					Subscribe
				</Button>
			} basic size='small'>
				<Header icon='archive' content={`Subscribe to ${board.name}`} />
				<Modal.Content>
					<p>
						Are you sure you want to subscribe to this board?
					</p>
				</Modal.Content>
				<Modal.Actions>
					<Button basic color='red' inverted>
						<Icon name='remove' /> No
					</Button>
					<Button color='green' inverted onClick={this.yesOnClickHander}>
						<Icon name='checkmark' /> Yes
					</Button>
				</Modal.Actions>
			</Modal>
				
		)
	}
}

export default withRouter(withUtilsConsumer(SubscribeModal))
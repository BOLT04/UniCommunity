import React, { Component } from 'react'
import { Button, Header, Icon, Modal } from 'semantic-ui-react'

import { withRouter } from 'react-router-dom'

import { APPLICATION_JSON } from '../../../common/constants'
import routes from '../../../common/routes'
import { removeFunctionsFrom } from '../../../common/common'
import { rels } from '../../../common/rels-registery'

import { withUtilsConsumer } from '../../withUtilsConsumer'

class SubscribeModal extends Component {

	constructor(props) {
		super(props)

		this.state = {}
	}

	yesOnClickHander = async e => {
		const { board, utilsObj, firebase } = this.props
		const url = board.getHrefOfRel(rels.addMemberToBoard)
		const rsp = await utilsObj.asyncRelativeFetch(rels.addMemberToBoard)
		const { _templates: { default: reqInfo } } = await utilsObj.asyncParseHalFormRsp(rsp)
		
		try {
			const messaging = firebase.messaging()
			const token = await messaging.getToken()
			
			const body = { token }
			const rsp = await utilsObj.asyncRelativeHttpRequest(url, reqInfo.method, APPLICATION_JSON, body)
			if (!rsp.ok) throw rsp

			const redirectPath = routes.getBoardUri(board.id)
			this.props.history.push(redirectPath, { board: removeFunctionsFrom(board) })
		} catch (error) {
			console.error({error})
		}
	}

  	

	render() {
		const { board } = this.props

		return (
			<Modal trigger={
				<Button primary content='Subscribe' />
			} basic size='small'>
				<Header icon='bell' content={`Subscribe to ${board.name}`} />
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
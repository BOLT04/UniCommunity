import React, { Component } from 'react'
import { Button, Header, Icon, Modal } from 'semantic-ui-react'

import { withRouter } from 'react-router-dom'

import routes from '../../../common/routes'
import { rels } from '../../../common/rels-registery'

import { withUtilsConsumer } from '../../withUtilsConsumer'

class UnsubscribeModal extends Component {
	
	constructor(props) {
		super(props)

		this.state = {}
	}

	closeModal = () => this.setState({ open: false })
	
	showModal = () => this.setState({ open: true })

	yesOnClickHander = async e => {
		const { board, utilsObj } = this.props
		const url = board.getHrefOfRel(rels.removeMemberToBoard)
		const rsp = await utilsObj.asyncRelativeFetch(rels.removeMemberToBoard)
    	const { _templates: { default: reqInfo } } = await utilsObj.asyncParseHalFormRsp(rsp)
		try {
			const rsp = await this.props.utilsObj.asyncRelativeHttpRequest(url, reqInfo.method)
			if (!rsp.ok) {}//TODO: handle error

			const redirectPath = routes.boards// TODO: redirect to /myboards or my home dashboard
			this.props.history.push(redirectPath)
		} catch(e) {
			//TODO: handle error
		}
	}

	render() {
		const { board } = this.props

		return (
			<Modal
				open={this.state.open}
				trigger={
					<Button color='red' content='Unsubscribe' onClick={this.showModal}/>
				} 
				basic 
				size='small'>
					<Header icon='archive' content={`Unsubscribe to ${board.name}`} />
					<Modal.Content>
						<p>
							Are you sure you want to unsubscribe to this board? If you say yes you will no longer
							receive notifications of new content and can't access the board's content.
						</p>
					</Modal.Content>
					<Modal.Actions>
						<Button basic color='red' inverted onClick={this.closeModal}>
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

export default withRouter(withUtilsConsumer(UnsubscribeModal))
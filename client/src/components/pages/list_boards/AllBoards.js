import React from 'react'
import PropTypes from 'prop-types'

import { List } from 'semantic-ui-react'

import asyncCollectionRspToList from '../../../service/mapper/collectionJson-mapper'
import CreateBoardApi from '../../../service/CreateBoardApi'

import { BoardListItem } from './BoardListItem'
import ListLoader from '../../common/ListLoader'
import Paginator from './Paginator'
import BookmarkableComponent from '../../common/BookmarkableComponent'

import { COLLECTION_JSON, httpStatus } from '../../../common/constants'
import routes from '../../../common/routes'
import { rels } from '../../../common/rels-registery'

import { withUtilsConsumer } from '../../withUtilsConsumer'

class AllBoards extends BookmarkableComponent {
	static propTypes = {
		api: PropTypes.instanceOf(CreateBoardApi)
	}

	constructor(props) {
		super(props)

		this.state = {}
		this.addServerHrefOf(rels.getBoards, { checkState: true })
	}

	//TODO: move this to the parent class, its the same in every child!
	async componentDidMount() {
		if (this.addServerHrefOf(rels.getBoards))
			await this.fetchData(this.serverHref)
	}

	async componentDidUpdate(prevProps) {
		if (this.props.home !== prevProps.home && this.addServerHrefOf(rels.getBoards))
			await this.fetchData(this.serverHref)
	}

	async fetchData(url) {
		const rsp = await this.props.utilsObj.asyncRelativeFetch(url, COLLECTION_JSON)
		if (rsp.status == httpStatus.UNAUTHORIZED) {
			this.props.history.push(routes.login, { 
				redirectTo: this.props.location.pathname,
				error: rsp
			})
			return
		}
		const body = await asyncCollectionRspToList(rsp)

		this.setState({ body })
	}

	renderBoards = () => (
		<List animated>
			{ this.state.body.items.map(b => <BoardListItem board={b} />) }
		</List>
	)

	handlePagination = url => this.fetchData(url)

	render() {
		const { body } = this.state || {}
		const list = body && body.items || null

		return (
			<>
				<ListLoader
					list={list}
					emptyListHeaderMsg='No Boards available'
					emptyListMsg='Consider creating a board first.'
					render={this.renderBoards}
				/>

				{body &&
					<Paginator body={body} onClick={this.handlePagination} />
				}
			</>
		)
	}
}

export default withUtilsConsumer(AllBoards)
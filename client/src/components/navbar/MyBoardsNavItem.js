import React, { Component } from 'react'
import { Link } from 'react-router-dom'

import { Dropdown, Menu } from 'semantic-ui-react'

import routes from '../../common/routes'
import { removeFunctionsFrom } from '../../common/common'
import { COLLECTION_JSON } from '../../common/constants'
import asyncCollectionRspToList from '../../service/mapper/collectionJson-mapper'

import { withUtilsConsumer } from '../withUtilsConsumer'

class MyBoardsNavItem extends Component {

    state = {
        options: []
    }

    async componentDidMount() {
        const { serverHref } = this.props
        const rsp = await this.props.utilsObj.asyncRelativeFetch(serverHref, COLLECTION_JSON)
        const items = (await asyncCollectionRspToList(rsp)).items
        const options = items.map((i, idx) => ({ key: i.id, text: <BoardDropItem board={i} />, value: idx }))

        this.setState({ options })
    }

    render() {
        const { options } = this.state

        return (
            <Menu compact inverted>
                <Dropdown text='My Boards' options={options} simple item/>
            </Menu>
        )
    }
}

const BoardDropItem = ({ board }) => (
    <Link to={{
        pathname: routes.getBoardUri(board.id),
        state: { board: removeFunctionsFrom(board) }
    }}>
        {board.name}
    </Link>
)

export default withUtilsConsumer(MyBoardsNavItem)

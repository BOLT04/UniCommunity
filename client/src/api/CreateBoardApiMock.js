import CreateBoardApi from './CreateBoardApi'

export default class CreateBoardApiMock extends CreateBoardApi {

    createBoard = () => Promise.resolve({
        name: 'DAW Board',
        _links: {
            self: { href: "/board/DAW-Board" }
        }  
    })
}

package pt.isel.g20.unicommunity.navigation.service

import pt.isel.g20.unicommunity.board.model.Board

interface INavigationService {
    fun getMyBoards(userId: Long) : Iterable<Board>
}
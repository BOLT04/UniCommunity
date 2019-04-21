package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.board.model.Board

interface BoardRepository : CrudRepository<Board, Long>
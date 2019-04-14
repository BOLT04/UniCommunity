package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.user.model.User


interface UserRepository : CrudRepository<User, Long>

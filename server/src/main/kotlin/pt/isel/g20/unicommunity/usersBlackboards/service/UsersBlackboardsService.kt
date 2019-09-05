package pt.isel.g20.unicommunity.usersBlackboards.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.ADMIN
import pt.isel.g20.unicommunity.common.ForbiddenException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.common.NotFoundUsersBlackboardsException
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.repository.UsersBlackboardsRepository
import pt.isel.g20.unicommunity.user.model.User
import pt.isel.g20.unicommunity.usersBlackboards.model.UsersBlackboards

@Service
class UsersBlackboardsService(val usersBlackboardsRepo: UsersBlackboardsRepository,
                              val usersRepo: UserRepository) {

    fun getAllUsersBlackboards(sessionUser: User, userId: Long): Iterable<UsersBlackboards>{
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        if(user.id != sessionUser.id && sessionUser.role != ADMIN) throw ForbiddenException()

        return usersBlackboardsRepo.findAll()
    }

    fun getUsersBlackboardById(sessionUser: User, userId: Long, bbId: Long) : UsersBlackboards{
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        if(user.id != sessionUser.id && sessionUser.role != ADMIN) throw ForbiddenException()

        return usersBlackboardsRepo.findByUserIdAndBlackboardId(userId, bbId) ?: throw NotFoundUsersBlackboardsException()
    }

    fun editUsersBlackboard(
            sessionUser: User,
            userId: Long,
            bbId: Long,
            notificationLevel: String
    ): UsersBlackboards {
        val usersBlackboard = getUsersBlackboardById(sessionUser, userId, bbId)
        usersBlackboard.notificationLevel = notificationLevel

        return usersBlackboardsRepo.save(usersBlackboard)
    }

}
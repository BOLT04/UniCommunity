package pt.isel.g20.unicommunity.config

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.model.User

@Component
class DataLoader(
        val userRepository: UserRepository
        //val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val user = User(
                "Admin",
                "admin@gmail.com",
                BCryptPasswordEncoder().encode("admin"),
                "admin",
                "gitAdmin"
        )
        userRepository.save(user)

        val foundUser = userRepository.findByEmail("admin@gmail.com") ?: throw NotFoundUserException()
        System.out.println(foundUser.id.toString() + " - " + foundUser.name + " - " + foundUser.email+ " - " + foundUser.pw)

    }
}
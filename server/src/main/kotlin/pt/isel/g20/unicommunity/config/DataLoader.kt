package pt.isel.g20.unicommunity.config

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.template.model.Template
import pt.isel.g20.unicommunity.user.model.User

@Component
class DataLoader(
        val templatesRepo: TemplateRepository,
        val usersRepo: UserRepository
        //val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val templates =
                mutableListOf(
                        Template(
                                "Basic board w/ forum",
                                true,
                                "Anuncios, Sumarios"
                        ),
                        Template(
                                "Basic board w/o forum",
                                false,
                                "Anuncios, Sumarios"
                        ),
                        Template(
                                "Advanced Board",
                                true,
                                "Anuncios, Sumarios, Bibliografia"
                        )
                )

        templatesRepo.saveAll(templates)

        val user = User(
                "Admin",
                "admin@gmail.com",
                BCryptPasswordEncoder().encode("admin"),
                "admin",
                "gitAdmin"
        )
        usersRepo.save(user)
    }
}
package pt.isel.g20.unicommunity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories("pt.isel.g20.unicommunity.repository")
@EntityScan("pt.isel.g20.unicommunity.user.model")
@SpringBootApplication
class UniCommunityApplication

fun main(args: Array<String>) {
	runApplication<UniCommunityApplication>(*args)
}

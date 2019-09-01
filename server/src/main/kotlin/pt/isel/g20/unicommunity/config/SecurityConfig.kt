package pt.isel.g20.unicommunity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll()

        http.csrf().disable();
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
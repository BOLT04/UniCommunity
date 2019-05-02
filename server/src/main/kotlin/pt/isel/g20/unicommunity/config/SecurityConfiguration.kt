package pt.isel.g20.unicommunity.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import pt.isel.g20.unicommunity.userDetails.service.CustomUserDetailsService
import java.lang.RuntimeException

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
class SecurityConfiguration(val userDetailsService: CustomUserDetailsService) : WebSecurityConfigurerAdapter(){

    val passwordEncoder = object : PasswordEncoder{
        override fun encode(rawPassword: CharSequence?) = rawPassword.toString()

        override fun matches(rawPassword: CharSequence?, encodedPassword: String?) = rawPassword.toString() == encodedPassword
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        if(auth == null) throw RuntimeException("Wow AUTH security error")
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity?) {
        if(http == null) throw RuntimeException("Wow HTTP security error")
        http.csrf()?.disable()
        http
                .authorizeRequests()
                .antMatchers("**/boards/**")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .permitAll()
    }
}
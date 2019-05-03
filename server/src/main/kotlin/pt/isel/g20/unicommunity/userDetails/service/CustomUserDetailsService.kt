package pt.isel.g20.unicommunity.userDetails.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.userDetails.model.CustomUserDetails

@Service
class CustomUserDetailsService(private val usersRepo: UserRepository) : UserDetailsService{

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = usersRepo.findByName(username) ?: throw UsernameNotFoundException("Username not found!")

        return CustomUserDetails(user)
    }

}
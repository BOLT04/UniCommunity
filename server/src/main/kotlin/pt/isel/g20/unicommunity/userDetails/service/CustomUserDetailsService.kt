package pt.isel.g20.unicommunity.userDetails.service

import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.repository.UserRepository
/*
@Service
class CustomUserDetailsService(private val usersRepo: UserRepository) : UserDetailsService{

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = usersRepo.findByEmail(username) ?: throw UsernameNotFoundException("Username not found!")

        return CustomUserDetails(user)
    }

}
*/
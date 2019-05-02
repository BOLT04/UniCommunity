package pt.isel.g20.unicommunity.userDetails.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pt.isel.g20.unicommunity.user.model.User

class CustomUserDetails(): User(), UserDetails{

    constructor(user: User) : this() {
        super.id = user.id
        super.name = user.name
        super.email = user.email
        super.pw = user.pw
        super.role = user.role
        super.githubId = user.githubId
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val teacher = SimpleGrantedAuthority("ROLE_TEACHER")
        val student = SimpleGrantedAuthority("ROLE_STUDENT")

        val authorities = mutableListOf<SimpleGrantedAuthority>()
        authorities.add(teacher)
        authorities.add(student)
        return authorities
    }

    override fun isEnabled() = true

    override fun getUsername() = super.name

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = super.pw

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

}
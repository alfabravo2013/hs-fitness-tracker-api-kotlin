package fitnesstracker.security

import fitnesstracker.developer.Developer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(val developer: Developer) : UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> = listOf(SimpleGrantedAuthority("app:register"))

    override fun getPassword(): String = developer.password

    override fun getUsername(): String = developer.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}

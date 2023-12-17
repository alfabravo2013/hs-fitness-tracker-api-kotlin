package fitnesstracker.security

import fitnesstracker.developer.DeveloperRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val developerRepository: DeveloperRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return developerRepository.findByEmail(username.lowercase())
            .map { SecurityUser(it) }
            .orElseThrow { UsernameNotFoundException("Username '$username' not found") }
    }
}

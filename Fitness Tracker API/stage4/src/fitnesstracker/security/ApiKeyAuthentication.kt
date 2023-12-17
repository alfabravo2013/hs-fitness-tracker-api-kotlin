package fitnesstracker.security

import fitnesstracker.application.Application
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class ApiKeyAuthentication(
    private val application: Application?,
    private val apikey: String
) : Authentication {
    private var isAuthenticated = false

    override fun getName(): String = application?.name ?: "Application is null"

    override fun getAuthorities(): List<GrantedAuthority> = listOf(SimpleGrantedAuthority("application"))

    override fun getCredentials(): Any = apikey

    override fun getDetails(): Any = "nothing here"

    override fun getPrincipal(): Any = application ?: "Application is null"

    override fun isAuthenticated(): Boolean = isAuthenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.isAuthenticated = isAuthenticated
    }
}

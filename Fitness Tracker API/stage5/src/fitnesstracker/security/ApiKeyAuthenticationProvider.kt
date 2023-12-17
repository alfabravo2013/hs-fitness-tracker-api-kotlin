package fitnesstracker.security

import fitnesstracker.application.ApplicationRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication

class ApiKeyAuthenticationProvider(
    private val applicationRepository: ApplicationRepository
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val apikey: String = authentication.credentials.toString()
        val application = applicationRepository.findByApiKey(apikey)
            .orElseThrow { BadCredentialsException("Unknown API key") }
        val apiKeyAuthentication = ApiKeyAuthentication(application, apikey)
        apiKeyAuthentication.isAuthenticated = true
        return apiKeyAuthentication
    }

    override fun supports(authentication: Class<*>): Boolean {
        return ApiKeyAuthentication::class.java == authentication
    }
}

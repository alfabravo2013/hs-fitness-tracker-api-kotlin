package fitnesstracker.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

class ApiKeyAuthenticationFilter(
    private val manager: AuthenticationManager
) : OncePerRequestFilter() {
    private val matcher = AntPathRequestMatcher("/api/tracker")
    private val authenticationEntryPoint = AuthenticationEntryPoint { _, response, ex ->
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(ex.message ?: "Authentication entry point invoked with null ex.message")
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (matcher.matches(request)) {
            val apikey = request.getHeader("X-API-Key")
            try {
                if (apikey != null) {
                    val unauthenticated: Authentication = ApiKeyAuthentication(null, apikey)
                    val authenticated = manager.authenticate(unauthenticated)
                    SecurityContextHolder.getContext().authentication = authenticated
                    filterChain.doFilter(request, response)
                    return
                }
                throw BadCredentialsException("No API key")
            } catch (e: AuthenticationException) {
                authenticationEntryPoint.commence(request, response, e)
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}

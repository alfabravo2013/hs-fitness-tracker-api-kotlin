package fitnesstracker.security

import fitnesstracker.application.ApplicationRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val appUserDetailsService: AppUserDetailsService,
    private val applicationRepository: ApplicationRepository
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.POST, "/api/developers/signup").permitAll()
                    .requestMatchers("/error", "/actuator/shutdown", "/api/tracker").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterAfter(
                ApiKeyAuthenticationFilter(authManager()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .csrf { it.disable() }
            .build()
    }

    @Bean
    fun authManager(): AuthenticationManager {
        val daoAuthProvider = DaoAuthenticationProvider()
        daoAuthProvider.setUserDetailsService(appUserDetailsService)
        daoAuthProvider.setPasswordEncoder(passwordEncoder())
        val apiKeyAuthProvider = ApiKeyAuthenticationProvider(applicationRepository)
        return ProviderManager(daoAuthProvider, apiKeyAuthProvider)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}

package fitnesstracker.developer

import fitnesstracker.common.FitnessTrackerApiException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DeveloperService(
    private val repository: DeveloperRepository,
    private val encoder: PasswordEncoder
) {
    fun registerDeveloper( request: DeveloperRegistrationRequest): String {
        try {
            val entity = Developer(
                email = request.email.lowercase(),
                password = encoder.encode(request.password)
            )
            return requireNotNull(repository.save(entity).id)
        } catch (e: DataIntegrityViolationException) {
            throw FitnessTrackerApiException("Email already taken")
        }
    }

    fun getProfile(id: String): DeveloperProfile {
        val developer = repository.findById(id)
            .orElseThrow { FitnessTrackerApiException("Profile not found") }

        return DeveloperProfile(
            id = requireNotNull(developer.id),
            email = developer.email
        )
    }
}

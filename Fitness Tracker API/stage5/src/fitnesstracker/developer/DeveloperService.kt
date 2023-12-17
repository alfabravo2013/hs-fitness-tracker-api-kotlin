package fitnesstracker.developer

import fitnesstracker.application.ApplicationProfile
import fitnesstracker.common.FitnessTrackerApiException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeveloperService(
    private val repository: DeveloperRepository,
    private val encoder: PasswordEncoder
) {
    fun registerDeveloper(request: DeveloperRegistrationRequest): String {
        try {
            val entity = Developer(
                email = request.email.lowercase(),
                password = encoder.encode(request.password),
                applications = mutableSetOf()
            )
            return requireNotNull(repository.save(entity).id)
        } catch (e: DataIntegrityViolationException) {
            throw FitnessTrackerApiException("Email already taken")
        }
    }

    @Transactional
    fun updateDeveloper(developer: Developer) = repository.save(developer)

    fun getProfile(id: String): DeveloperProfile {
        val developer = repository.findById(id)
            .orElseThrow { FitnessTrackerApiException("Profile not found") }

        return DeveloperProfile(
            id = requireNotNull(developer.id),
            email = developer.email,
            applications = developer.applications
                .sortedByDescending { application -> application.timestamp }
                .map { application ->
                    ApplicationProfile(
                        id = requireNotNull(application.id),
                        name = application.name,
                        description = application.description,
                        apikey = application.apiKey, category = application.category
                    )
                }
        )
    }
}

package fitnesstracker.application

import fitnesstracker.common.FitnessTrackerApiException
import fitnesstracker.developer.Developer
import fitnesstracker.developer.DeveloperService
import org.springframework.security.crypto.keygen.KeyGenerators
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger

@Service
class ApplicationService(private val developerService: DeveloperService) {

    @Transactional
    fun registerApplication(
        request: ApplicationRegistrationRequest,
        developer: Developer): ApiKeyDto {
        val isNonUniqueAppName = developer.applications.map { it.name }.any { it == request.name }
        if (isNonUniqueAppName) {
            throw FitnessTrackerApiException("Application with this name already exists")
        }

        val apikey = generateApiKey()
        val application = Application(
            name = request.name,
            description = request.description,
            apiKey = apikey,
            developer = developer,
            category = request.category
        )

        developer.applications.add(application)
        developerService.updateDeveloper(developer)

        return ApiKeyDto(
            name = application.name,
            apikey = application.apiKey,
            category = application.category
        )
    }

    @Transactional
    fun regenerateApiKey(id: String, developer: Developer): ApiKeyDto {
        val application = developer.applications.firstOrNull { app -> app.id == id }
            ?: throw FitnessTrackerApiException("Application not found")

        val apikey = generateApiKey()
        application.apiKey = apikey
        developerService.updateDeveloper(developer)

        return ApiKeyDto(
            name = application.name,
            apikey = application.apiKey,
            category = application.category
        )
    }

    private fun generateApiKey(): String {
        val bytes = KeyGenerators.secureRandom(20).generateKey()
        return BigInteger(1, bytes).toString(16)
    }
}

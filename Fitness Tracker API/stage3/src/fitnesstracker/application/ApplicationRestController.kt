package fitnesstracker.application

import fitnesstracker.security.SecurityUser
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ApplicationRestController(private val applicationService: ApplicationService) {

    @PostMapping("/api/applications/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
        @Valid @RequestBody request: ApplicationRegistrationRequest,
        @AuthenticationPrincipal securityUser: SecurityUser
    ): ApiKeyDto {
        return applicationService.registerApplication(request, securityUser.developer)
    }

    @PostMapping("/api/applications/{id}/apikey")
    @PreAuthorize("@dm.decide(#user, #id")
    fun recreateApiKey(
        @PathVariable id: String,
        @AuthenticationPrincipal securityUser: SecurityUser
    ): ApiKeyDto {
        return applicationService.regenerateApiKey(id, securityUser.developer)
    }
}

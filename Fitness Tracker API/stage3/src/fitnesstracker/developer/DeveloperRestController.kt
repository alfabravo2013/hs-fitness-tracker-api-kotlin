package fitnesstracker.developer

import fitnesstracker.security.SecurityUser
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class DeveloperRestController(private val service: DeveloperService) {

    @PostMapping("/api/developers/signup")
    fun register(@Valid @RequestBody request: DeveloperRegistrationRequest): ResponseEntity<Void> {
        val id = service.registerDeveloper(request)
        return ResponseEntity.created(URI.create("/api/developers/$id"))
            .build()
    }

    @GetMapping("/api/developers/{id}")
    @PreAuthorize("#user.developer.id == #id")
    fun getProfile(
        @PathVariable id: String,
        @AuthenticationPrincipal user: SecurityUser
    ): DeveloperProfile {
        return service.getProfile(id)
    }
}

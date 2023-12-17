package fitnesstracker.tracker

import fitnesstracker.application.Application
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class TrackerRestController(
    private val service: FitnessDataService,
    private val rateLimiter: RateLimiter
) {

    @PostMapping("/api/tracker")
    @ResponseStatus(HttpStatus.CREATED)
    fun postData(
        @RequestBody request: DataUploadRequest,
        @AuthenticationPrincipal application: Application
    ) {
        tryMakeRequest(application)
        service.insertData(request, application)
    }

    @GetMapping("/api/tracker")
    fun getData(@AuthenticationPrincipal application: Application): List<DataDto> {
        tryMakeRequest(application)
        return service.getAllData()
    }

    private fun tryMakeRequest(application: Application) {
        if (application.category == "basic") {
            val isGranted = rateLimiter.isGranted(application.apiKey)
            if (!isGranted) {
                throw RateLimitExceededException()
            }
        }
    }
}

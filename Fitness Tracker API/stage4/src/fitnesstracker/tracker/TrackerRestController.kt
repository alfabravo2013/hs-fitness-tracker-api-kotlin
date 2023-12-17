package fitnesstracker.tracker

import fitnesstracker.application.Application
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class TrackerRestController(private val service: FitnessDataService) {

    @PostMapping("/api/tracker")
    @ResponseStatus(HttpStatus.CREATED)
    fun postData(
        @RequestBody request: DataUploadRequest,
        @AuthenticationPrincipal application: Application
    ) {
        service.insertData(request, application)
    }

    @GetMapping("/api/tracker")
    fun getData(): List<DataDto> = service.getAllData()
}

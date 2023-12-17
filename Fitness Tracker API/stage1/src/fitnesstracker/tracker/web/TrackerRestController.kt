package fitnesstracker.tracker.web

import fitnesstracker.tracker.service.FitnessDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TrackerRestController(private val service: FitnessDataService) {

    @PostMapping("/api/tracker")
    @ResponseStatus(HttpStatus.CREATED)
    fun postData(@RequestBody request: DataUploadRequest) = service.insertData(request)

    @GetMapping("/api/tracker")
    fun getData(): List<DataDto> = service.getAllData()
}

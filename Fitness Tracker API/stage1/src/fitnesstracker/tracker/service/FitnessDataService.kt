package fitnesstracker.tracker.service

import fitnesstracker.tracker.mapper.toDto
import fitnesstracker.tracker.model.FitnessData
import fitnesstracker.tracker.repository.FitnessDataRepository
import fitnesstracker.tracker.web.DataDto
import fitnesstracker.tracker.web.DataUploadRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FitnessDataService(private val repository: FitnessDataRepository) {

    fun insertData(request: DataUploadRequest) {
        val entity = FitnessData(
            username = request.username,
            activity = request.activity,
            ducation = request.duration,
            calories = request.calories
        )

        repository.save(entity)
    }

    fun getAllData(): List<DataDto> {
        val sort = Sort.by("timestamp").descending()
        return repository.findAll(sort).map { entity -> entity.toDto() }
    }
}

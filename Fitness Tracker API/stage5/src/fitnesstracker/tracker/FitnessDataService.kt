package fitnesstracker.tracker

import fitnesstracker.application.Application
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FitnessDataService(private val repository: FitnessDataRepository) {

    fun insertData(request: DataUploadRequest, application: Application) {
        val entity = FitnessData(
            username = request.username,
            activity = request.activity,
            duration = request.duration,
            calories = request.calories,
            application = application.name
        )

        repository.save(entity)
    }

    fun getAllData(): List<DataDto> {
        val sort = Sort.by("timestamp").descending()
        return repository.findAll(sort).map { entity -> entity.toDto() }
    }
}

fun FitnessData.toDto(): DataDto {
    return DataDto(
        id = requireNotNull(this.id),
        username = this.username,
        activity = this.activity,
        duration = this.duration,
        calories = this.calories,
        application = this.application
    )
}

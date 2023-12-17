package fitnesstracker.tracker.mapper

import fitnesstracker.tracker.model.FitnessData
import fitnesstracker.tracker.web.DataDto

fun FitnessData.toDto(): DataDto {
    return DataDto(
        id = requireNotNull(this.id),
        username = this.username,
        activity = this.activity,
        duration = this.ducation,
        calories = this.calories
    )
}

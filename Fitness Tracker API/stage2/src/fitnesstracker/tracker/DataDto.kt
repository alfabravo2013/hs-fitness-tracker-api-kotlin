package fitnesstracker.tracker

data class DataDto(
    val id: String,
    val username: String,
    val activity: String,
    val duration: Int,
    val calories: Int
)

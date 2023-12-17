package fitnesstracker.tracker

data class DataUploadRequest(
    val username: String,
    val activity: String,
    val duration: Int,
    val calories: Int
)

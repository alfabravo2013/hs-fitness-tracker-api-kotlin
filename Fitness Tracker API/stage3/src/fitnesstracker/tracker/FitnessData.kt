package fitnesstracker.tracker

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
class FitnessData(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    var username: String,
    var activity: String,
    var duration: Int,
    var calories: Int,
    var timestamp: Instant = Instant.now()
)

package fitnesstracker.developer

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface DeveloperRepository : JpaRepository<Developer, String> {
    fun findByEmail(email: String): Optional<Developer>
}

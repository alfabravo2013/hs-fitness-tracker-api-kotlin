package fitnesstracker.application

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ApplicationRepository : JpaRepository<Application, String> {
    fun findByApiKey(apikey: String): Optional<Application>
}

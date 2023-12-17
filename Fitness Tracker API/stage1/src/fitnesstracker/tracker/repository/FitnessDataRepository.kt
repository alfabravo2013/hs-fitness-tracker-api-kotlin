package fitnesstracker.tracker.repository

import fitnesstracker.tracker.model.FitnessData
import org.springframework.data.jpa.repository.JpaRepository

interface FitnessDataRepository : JpaRepository<FitnessData, String>

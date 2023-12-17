package fitnesstracker.tracker

import fitnesstracker.tracker.FitnessData
import org.springframework.data.jpa.repository.JpaRepository

interface FitnessDataRepository : JpaRepository<FitnessData, String>

package fitnesstracker.tracker

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

@Component
class RateLimiter {
    private val tokenBukets: MutableMap<String, Int> = ConcurrentHashMap()

    fun isGranted(apikey: String): Boolean {
        tokenBukets.putIfAbsent(apikey, INITIAL_TOKENS)
        val attempts = tokenBukets[apikey] ?: 0
        tokenBukets.computeIfPresent(apikey) { _, _ -> max(attempts - 1, 0) }
        return attempts > 0
    }

    @Scheduled(fixedDelay = REPLENISH_COOLDOWN)
    private fun replenish() {
        for (entry in tokenBukets.entries) {
            if (entry.value < MAX_TOKENS) {
                tokenBukets[entry.key] = entry.value + 1
            }
        }
    }

    companion object {
        private const val MAX_TOKENS = 1
        private const val INITIAL_TOKENS = 1
        private const val REPLENISH_COOLDOWN = 1000L
    }
}

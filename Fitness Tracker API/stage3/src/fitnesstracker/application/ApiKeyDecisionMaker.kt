package fitnesstracker.application

import fitnesstracker.security.SecurityUser
import org.springframework.stereotype.Component

@Component("dm")
class ApiKeyDecisionMaker {
    fun decide(securityUser: SecurityUser?, appId: String): Boolean {
        return securityUser?.developer?.applications?.any { it.id == appId } ?: false
    }
}

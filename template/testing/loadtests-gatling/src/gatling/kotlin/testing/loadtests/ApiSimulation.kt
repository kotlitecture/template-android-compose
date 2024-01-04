package testing.loadtests

import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.rampUsers
import io.gatling.javaapi.core.CoreDsl.rampUsersPerSec
import io.gatling.javaapi.core.CoreDsl.stressPeakUsers
import io.gatling.javaapi.core.PopulationBuilder
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import org.slf4j.LoggerFactory
import testing.loadtests.data.AccessTokenData

abstract class ApiSimulation : Simulation() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val apiEndpoint = http.baseUrl("https://api/v1")
        .sign { request, session ->
            val token = session.get<AccessTokenData?>(AccessTokenData.ID)
            logger.debug("token :: {} -> {}", request.uri, token)
            token
                ?.let { "Bearer ${it.accessToken}" }
                ?.let { request.headers.add("Authorization", it) }
            request
        }

    fun ScenarioBuilder.singleUser(): PopulationBuilder {
        return injectOpen(
            atOnceUsers(1)
        ).protocols(apiEndpoint)
    }

    fun ScenarioBuilder.multipleUsers(count: Int, during: Long = 10): PopulationBuilder {
        return injectOpen(
            stressPeakUsers(count).during(during)
        ).protocols(apiEndpoint)
    }

    fun ScenarioBuilder.multipleLow(): PopulationBuilder {
        return injectOpen(
            atOnceUsers(10),
            rampUsers(20).during(5),
            constantUsersPerSec(2.0).during(20),
            rampUsersPerSec(1.0).to(5.0).during(10),
            rampUsersPerSec(1.0).to(5.0).during(10).randomized(),
            stressPeakUsers(250).during(20)
        ).protocols(apiEndpoint)
    }

    fun ScenarioBuilder.multipleMedium(): PopulationBuilder {
        return injectOpen(
            atOnceUsers(10),
            rampUsers(30).during(5),
            constantUsersPerSec(20.0).during(15),
            constantUsersPerSec(20.0).during(15).randomized(),
            rampUsersPerSec(10.0).to(20.0).during(10),
            rampUsersPerSec(10.0).to(20.0).during(10).randomized(),
            stressPeakUsers(1000).during(20)
        ).protocols(apiEndpoint)
    }

    fun ScenarioBuilder.multipleHigh(): PopulationBuilder {
        return injectOpen(
            atOnceUsers(10),
            rampUsers(30).during(5),
            constantUsersPerSec(20.0).during(15),
            constantUsersPerSec(20.0).during(15).randomized(),
            rampUsersPerSec(10.0).to(20.0).during(10),
            rampUsersPerSec(10.0).to(20.0).during(10).randomized(),
            stressPeakUsers(5000).during(20)
        ).protocols(apiEndpoint)
    }

}
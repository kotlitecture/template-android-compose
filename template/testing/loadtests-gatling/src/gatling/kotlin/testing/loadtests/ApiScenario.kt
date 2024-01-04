package testing.loadtests

import com.fasterxml.jackson.databind.ObjectMapper
import io.gatling.javaapi.core.CoreDsl.StringBody
import io.gatling.javaapi.core.CoreDsl.bodyString
import io.gatling.javaapi.core.CoreDsl.jsonPath
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl.http
import org.slf4j.LoggerFactory
import testing.loadtests.data.AccessTokenData
import testing.loadtests.data.UserData
import testing.loadtests.feeder.IDataFeeder
import testing.loadtests.feeder.UserDataFeeder

abstract class ApiScenario {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val users: IDataFeeder<UserData> = UserDataFeeder()
    private val jsonMapper by lazy { ObjectMapper() }

    abstract fun scenario(): ScenarioBuilder

    protected fun create(name: String): ScenarioBuilder = scenario(name)

    protected fun toJson(from: Any): String = jsonMapper.writeValueAsString(from)
    protected fun <T> fromJson(from: String, type: Class<T>): T? = runCatching { jsonMapper.readValue(from, type) }
        .onFailure { logger.error("fromJson ($from)", it) }
        .getOrNull()

    fun ScenarioBuilder.postLogin(): ScenarioBuilder {
        return feed(users.raw()).postLogin("#{email}", "#{password}")
    }

    fun ScenarioBuilder.postLogin(email: String, password: String): ScenarioBuilder {
        val user = UserData(email, password)
        return exec(
            http("Login")
                .post("/Login")
                .body(StringBody(toJson(user)))
                .asJson()
                .check(
                    jsonPath("$.accessToken").ofString(),
                    jsonPath("$.refreshToken").ofString(),
                    bodyString()
                        .transform { fromJson(it, AccessTokenData::class.java) }
                        .saveAs(AccessTokenData.ID)
                )
                .transformResponse { response, session ->
                    logger.debug("request :: {}", response.request().body.toString())
                    response
                }
        )
    }

    fun ScenarioBuilder.getUser(): ScenarioBuilder {
        return exec(
            http("User")
                .get("/User")
                .transformResponse { response, session ->
                    val json = response.body().string()
                    logger.debug("response :: {}", json)
                    response
                }
        )
    }

    fun ScenarioBuilder.getUserCurrencies(): ScenarioBuilder {
        return exec(
            http("User/Currencies")
                .get("/User/Currencies")
        )
    }

    fun ScenarioBuilder.checkNotifications(): ScenarioBuilder {
        return exec(
            http("Notifications/Check")
                .get("/Notifications/Check")
        )
    }

    fun ScenarioBuilder.getFinInstruments(group: String, limit: Int = 40, offset: Int = 0): ScenarioBuilder {
        return exec(
            http("FinInstruments -> $group")
                .get("/FinInstruments")
                .queryParam("group", group)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
        )
    }

    fun ScenarioBuilder.getFinInstrumentsFav(): ScenarioBuilder {
        return exec(
            http("FinInstruments/Fav")
                .get("/FinInstruments/Fav")
        )
    }

    fun ScenarioBuilder.getFinInstrumentsRatingGroups(): ScenarioBuilder {
        return exec(
            http("FinInstruments/RatingGroups")
                .get("/FinInstruments/RatingGroups")
        )
    }

    fun ScenarioBuilder.getFinInstrumentsMarketGroups(): ScenarioBuilder {
        return exec(
            http("FinInstruments/Groups")
                .get("/FinInstruments/Groups")
        )
    }

}
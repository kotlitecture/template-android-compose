package testing.loadtests.scenario

import io.gatling.javaapi.core.ScenarioBuilder
import testing.loadtests.ApiScenario

internal class GuestScenario : ApiScenario() {

    override fun scenario(): ScenarioBuilder = create("guest navigation")
        .getFinInstrumentsRatingGroups()
        .getFinInstruments("popular")
        .pause(2)
        .getFinInstruments("winners")
        .pause(2)
        .getFinInstruments("top_movers")
        .pause(2)
        .getFinInstruments("loosers")

}
package testing.loadtests.scenario

import io.gatling.javaapi.core.ScenarioBuilder
import testing.loadtests.ApiScenario

class SignInScenario : ApiScenario() {

    override fun scenario(): ScenarioBuilder = create("sign in and main page preparation")
//        .getFinInstrumentsRatingGroups()
//        .getFinInstruments("popular")
//        .pause(2)
        .postLogin()
//        .getUser()
//        .getUserCurrencies()
//        .checkNotifications()
//        .pause(2)
//        .getFinInstrumentsFav()
//        .getFinInstrumentsMarketGroups()
//        .getFinInstruments("popular")

}
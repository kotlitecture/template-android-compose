package testing.loadtests.simulation

import testing.loadtests.ApiSimulation
import testing.loadtests.scenario.GuestScenario

class GuestSimulation : ApiSimulation() {

    init {
        setUp(GuestScenario().scenario().multipleHigh())
    }

}
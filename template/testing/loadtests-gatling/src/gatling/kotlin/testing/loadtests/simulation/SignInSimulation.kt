package testing.loadtests.simulation

import testing.loadtests.ApiSimulation
import testing.loadtests.scenario.SignInScenario

class SignInSimulation : ApiSimulation() {

    init {
        setUp(SignInScenario().scenario().multipleLow())
    }

}
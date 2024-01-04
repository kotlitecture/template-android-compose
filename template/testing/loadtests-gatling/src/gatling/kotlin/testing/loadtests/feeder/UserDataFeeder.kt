package testing.loadtests.feeder

import io.gatling.javaapi.core.CoreDsl.csv
import io.gatling.javaapi.core.FeederBuilder
import testing.loadtests.data.UserData

class UserDataFeeder : IDataFeeder<UserData> {

    private val users by lazy { csv("users.csv").eager().queue() }

    override fun raw(): FeederBuilder.Batchable<String> = users

}
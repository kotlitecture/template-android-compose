package testing.loadtests.feeder

import io.gatling.javaapi.core.FeederBuilder

interface IDataFeeder<D> {

    fun raw(): FeederBuilder.Batchable<String>

}
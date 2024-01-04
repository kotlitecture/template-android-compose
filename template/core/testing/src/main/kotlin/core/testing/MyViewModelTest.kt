package core.testing

import core.testing.util.MainDispatcherRule
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class MyViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        doSetup()
    }

    protected open fun doSetup() {}

}

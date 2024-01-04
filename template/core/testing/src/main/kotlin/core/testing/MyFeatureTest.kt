package core.testing

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class MyFeatureTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected fun getString(id: Int, vararg args: Any) = composeTestRule.activity.resources.getString(id, *args)

    @Before
    fun setup() {
        doSetup()
    }

    protected open fun doSetup() {}

}

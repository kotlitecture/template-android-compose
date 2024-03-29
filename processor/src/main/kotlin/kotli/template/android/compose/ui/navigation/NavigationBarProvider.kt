package kotli.template.android.compose.ui.navigation

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.navigation.adaptive.AdaptiveNavigationProcessor
import kotli.template.android.compose.ui.navigation.bottom.BottomNavigationProcessor
import kotli.template.android.compose.ui.navigation.left.LeftNavigationProcessor
import kotli.template.android.compose.ui.navigation.top.TopNavigationProcessor

class NavigationBarProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.navigation"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.UI

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        CommonNavigationProcessor::class.java
    )

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        AdaptiveNavigationProcessor(),
        BottomNavigationProcessor(),
        LeftNavigationProcessor(),
        TopNavigationProcessor()
    )

}
package kotli.template.android.compose.ui.navigation

import kotli.engine.template.FileRule
import kotli.engine.template.FileRules

data class NavigationBarProviderRules(override val rules: List<FileRule>) :
    FileRules("app/src/main/kotlin/app/ui/navigation/NavigationBarProvider.kt", rules) {
    constructor(vararg rules: FileRule) : this(rules.toList())
    constructor(rule: FileRule) : this(listOf(rule))
}
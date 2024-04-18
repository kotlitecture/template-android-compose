package kotli.template.android.compose

import kotli.engine.template.FileRule
import kotli.engine.template.FileRules

data class NavigationBarStateRules(override val rules: List<FileRule>) :
    FileRules("app/src/main/kotlin/app/di/state/ProvidesNavigationBarState.kt", rules) {
    constructor(vararg rules: FileRule) : this(rules.toList())
    constructor(rule: FileRule) : this(listOf(rule))
}
package kotli.template.android.compose

import kotli.engine.template.FileRule
import kotli.engine.template.FileRules

data class AppStartupInitializerRules(override val rules: List<FileRule>) :
    FileRules("app/src/main/kotlin/app/AppStartupInitializer.kt", rules) {
    constructor(vararg rules: FileRule) : this(rules.toList())
    constructor(rule: FileRule) : this(listOf(rule))
}
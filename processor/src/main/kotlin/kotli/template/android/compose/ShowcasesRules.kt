package kotli.template.android.compose

import kotli.engine.template.FileRule
import kotli.engine.template.FileRules

data class ShowcasesRules(override val rules: List<FileRule>) :
    FileRules("app/src/main/kotlin/app/showcases/Showcases.kt", rules) {
    constructor(vararg rules: FileRule) : this(rules.toList())
    constructor(rule: FileRule) : this(listOf(rule))
}
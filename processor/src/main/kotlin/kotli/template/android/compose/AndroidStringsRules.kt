package kotli.template.android.compose

import kotli.engine.template.FileRule
import kotli.engine.template.FileRules

data class AndroidStringsRules(override val rules: List<FileRule>) : FileRules("*/strings.xml", rules) {
    constructor(vararg rules: FileRule) : this(rules.toList())
    constructor(rule: FileRule) : this(listOf(rule))
}
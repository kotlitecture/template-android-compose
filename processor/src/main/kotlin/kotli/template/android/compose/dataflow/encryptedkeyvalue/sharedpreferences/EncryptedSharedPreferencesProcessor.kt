package kotli.template.android.compose.dataflow.encryptedkeyvalue.sharedpreferences

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.encryptedkeyvalue.AppEncryptedKeyValueProcessor
import kotli.template.android.compose.dataflow.keyvalue.CoreSharedPreferencesKeyValueProcessor
import kotlin.time.Duration.Companion.hours

object EncryptedSharedPreferencesProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.encryptedkeyvalue.sharedpreferences"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences"
    override fun getIntegrationUrl(state: TemplateState): String = "https://proandroiddev.com/encrypted-preferences-in-android-af57a89af7c8"
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        CoreSharedPreferencesKeyValueProcessor::class.java,
        AppEncryptedKeyValueProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("core/data/build.gradle", CleanupMarkedLine("{dataflow.encryptedkeyvalue.sharedpreferences}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data/build.gradle", RemoveMarkedLine("{dataflow.encryptedkeyvalue.sharedpreferences}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxSecurityCrypto")))
        state.onApplyRules("*/EncryptedSharedPreferencesSource*", RemoveFile())
    }

}
package kotli.template.android.compose.userflow.passcode.full

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedBlock
import kotli.template.android.compose.AppNavigationRouterRules
import kotli.template.android.compose.NavigationStateRules
import kotli.template.android.compose.ShowcasesRules
import kotli.template.android.compose.dataflow.biometric.basic.BasicBiometricProcessor
import kotli.template.android.compose.dataflow.encryptedkeyvalue.sharedpreferences.EncryptedSharedPreferencesProcessor
import kotli.template.android.compose.dataflow.keyvalue.sharedpreferences.SharedPreferencesProcessor
import kotli.template.android.compose.showcases.passcode.PasscodeShowcasesProcessor
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor
import kotli.template.android.compose.ui.container.fixedtopbar.FixedTopBarProcessor
import kotli.template.android.compose.unspecified.startup.StartupInitializerProcessor
import kotlin.time.Duration.Companion.hours

object FullPasscodeProcessor : BaseFeatureProcessor() {

    const val ID = "userflow.passcode.full"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 16.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicBiometricProcessor::class.java,
        BasicComponentsProcessor::class.java,
        FixedTopBarProcessor::class.java,
        SharedPreferencesProcessor::class.java,
        StartupInitializerProcessor::class.java,
        EncryptedSharedPreferencesProcessor::class.java,
        PasscodeShowcasesProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            AppNavigationRouterRules(
                CleanupMarkedBlock("{userflow.passcode.full}")
            )
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "*app/src/main/kotlin/app/userflow/passcode",
            RemoveFile()
        )
        state.onApplyRules(
            "*strings.xml",
            RemoveMarkedLine("passcode_")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesPasscodeState.kt",
            RemoveFile()
        )
        state.onApplyRules(
            NavigationStateRules(
                RemoveMarkedLine("UnlockPasscodeDestination"),
                RemoveMarkedLine("SetPasscodeDestination"),
                RemoveMarkedLine("ConfirmPasscodeDestination"),
                RemoveMarkedLine("ResetPasscodeDestination"),
                RemoveMarkedLine("SetBiometricDestination"),
            )
        )
        state.onApplyRules(
            AppNavigationRouterRules(
                ReplaceMarkedBlock(
                    marker = "{userflow.passcode.full}",
                    replacer = "return ShowcasesDestination"
                ),
                RemoveMarkedLine("PasscodeRepository"),
                RemoveMarkedLine("UnlockPasscodeDestination"),
            )
        )
    }

}
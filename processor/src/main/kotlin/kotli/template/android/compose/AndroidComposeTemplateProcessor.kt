package kotli.template.android.compose

import kotli.engine.BaseTemplateProcessor
import kotli.engine.FeatureProvider
import kotli.engine.LayerType
import kotli.engine.TemplateState
import kotli.engine.model.LayerTypes
import kotli.engine.template.rule.ReplaceMarkedLine
import kotli.template.android.compose.appearance.l10n.L10NProvider
import kotli.template.android.compose.appearance.navigation.NavigationProvider
import kotli.template.android.compose.appearance.splash.SplashProvider
import kotli.template.android.compose.appearance.theme.ThemeProvider
import kotli.template.android.compose.dataflow.analytics.AnalyticsProvider
import kotli.template.android.compose.dataflow.api.ApiProvider
import kotli.template.android.compose.dataflow.config.ConfigProvider
import kotli.template.android.compose.dataflow.http.HttpProvider
import kotli.template.android.compose.dataflow.messaging.MessagingProvider
import kotli.template.android.compose.dataflow.storage.StorageProvider
import kotli.template.android.compose.dataflow.web3.Web3Provider
import kotli.template.android.compose.dataflow.work.WorkProvider
import kotli.template.android.compose.devops.distribution.DistributionProvider
import kotli.template.android.compose.devops.gradle.GradleProvider
import kotli.template.android.compose.devops.i18n.I18NProvider
import kotli.template.android.compose.devops.vcs.VcsProvider
import kotli.template.android.compose.quality.crashes.CrashesProvider
import kotli.template.android.compose.quality.performance.PerformanceProvider
import kotli.template.android.compose.quality.startup.StartupProvider
import kotli.template.android.compose.testing.http.HttpTestingProvider
import kotli.template.android.compose.testing.loadtests.LoadTestsProvider
import kotli.template.android.compose.testing.logging.LoggingProvider
import kotli.template.android.compose.transitive.TransitiveProvider
import kotli.template.android.compose.ui.preview.PreviewProvider
import kotli.template.android.compose.ui.screen.ScreenProvider
import kotli.template.android.compose.userflow.ads.AdsProvider
import kotli.template.android.compose.userflow.auth.AuthProvider
import kotli.template.android.compose.userflow.kyc.KycProvider
import kotli.template.android.compose.userflow.onboarding.OnboardingProvider
import kotli.template.android.compose.userflow.payments.PaymentsProvider
import kotli.template.android.compose.userflow.pincode.PincodeProvider
import kotli.template.android.compose.userflow.review.ReviewProvider
import kotli.template.android.compose.userflow.support.SupportProvider
import kotli.template.android.compose.userflow.update.UpdateProvider
import kotli.template.android.compose.userflow.webtonative.WebToNativeProvider

class AndroidComposeTemplateProcessor : BaseTemplateProcessor() {

    override fun getId(): String = ID
    override fun getType(): LayerType = LayerTypes.Android
    override fun getWebUrl(): String = "https://github.com/kotlitecture/template-android-compose"

    override fun createProviders(): List<FeatureProvider> = listOf(
        // appearance
        L10NProvider(),
        NavigationProvider(),
        SplashProvider(),
        ThemeProvider(),

        // build
        DistributionProvider(),
        GradleProvider(),
        I18NProvider(),
        VcsProvider(),

        // datasource
        AnalyticsProvider(),
        ApiProvider(),
        ConfigProvider(),
        HttpProvider(),
        MessagingProvider(),
        StorageProvider(),
        Web3Provider(),
        WorkProvider(),

        // quality
        CrashesProvider(),
        PerformanceProvider(),
        StartupProvider(),

        // ui
        PreviewProvider(),
        ScreenProvider(),

        // workflow
        AdsProvider(),
        AuthProvider(),
        KycProvider(),
        OnboardingProvider(),
        PaymentsProvider(),
        PincodeProvider(),
        ReviewProvider(),
        UpdateProvider(),
        SupportProvider(),
        WebToNativeProvider(),

        // Testing
        HttpTestingProvider(),
        LoadTestsProvider(),
        LoggingProvider(),

        // Transitive
        TransitiveProvider(),
    )

    override fun doPrepare(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            ReplaceMarkedLine(
                marker = "{applicationId}",
                replacer = "applicationId = '${state.layer.namespace}'"
            )
        )
        state.onApplyRules(
            "settings.gradle",
            ReplaceMarkedLine(
                marker = "{projectName}",
                replacer = "rootProject.name = '${state.layer.name}'"
            )
        )
    }

    companion object {
        const val ID = "template-android-compose"
    }

}
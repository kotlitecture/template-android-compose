package kotli.template.android.compose

import kotli.engine.AbstractTemplateGenerator
import kotli.engine.IFeatureProvider
import kotli.engine.TemplateContext
import kotli.engine.model.LayerType
import kotli.template.android.compose.appearance.l10n.L10NProvider
import kotli.template.android.compose.appearance.navigation.NavigationProvider
import kotli.template.android.compose.appearance.splash.SplashProvider
import kotli.template.android.compose.appearance.theme.ThemeProvider
import kotli.template.android.compose.build.distribution.DistributionProvider
import kotli.template.android.compose.build.gradle.GradleProvider
import kotli.template.android.compose.build.i18n.I18NProvider
import kotli.template.android.compose.build.vcs.VcsProvider
import kotli.template.android.compose.datasource.analytics.AnalyticsProvider
import kotli.template.android.compose.datasource.api.ApiProvider
import kotli.template.android.compose.datasource.config.ConfigProvider
import kotli.template.android.compose.datasource.http.HttpProvider
import kotli.template.android.compose.datasource.messaging.MessagingProvider
import kotli.template.android.compose.datasource.storage.StorageProvider
import kotli.template.android.compose.datasource.web3.Web3Provider
import kotli.template.android.compose.datasource.work.WorkProvider
import kotli.template.android.compose.quality.crashes.CrashesProvider
import kotli.template.android.compose.quality.performance.PerformanceProvider
import kotli.template.android.compose.quality.startup.StartupProvider
import kotli.template.android.compose.testing.http.HttpTestingProvider
import kotli.template.android.compose.testing.loadtests.LoadTestsProvider
import kotli.template.android.compose.testing.logging.LoggingProvider
import kotli.template.android.compose.transitive.TransitiveProvider
import kotli.template.android.compose.ui.preview.PreviewProvider
import kotli.template.android.compose.ui.screen.ScreenProvider
import kotli.template.android.compose.workflow.ads.AdsProvider
import kotli.template.android.compose.workflow.auth.AuthProvider
import kotli.template.android.compose.workflow.kyc.KycProvider
import kotli.template.android.compose.workflow.onboarding.OnboardingProvider
import kotli.template.android.compose.workflow.payments.PaymentsProvider
import kotli.template.android.compose.workflow.pincode.PincodeProvider
import kotli.template.android.compose.workflow.review.ReviewProvider
import kotli.template.android.compose.workflow.support.SupportProvider
import kotli.template.android.compose.workflow.update.UpdateProvider
import kotli.template.android.compose.workflow.webtonative.WebToNativeProvider
import org.springframework.stereotype.Component

@Component(TemplateGenerator.ID)
class TemplateGenerator : AbstractTemplateGenerator() {

    companion object {
        const val ID = "template-android-compose"
    }

    override val id: String = ID
    override val type: LayerType = LayerType.Android
    override val webUrl: String = "https://github.com/kotlitecture/template-android-compose"

    override fun createProviders(): List<IFeatureProvider> = listOf(
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

    override fun doPrepare(context: TemplateContext) {
        context.apply("app/build.gradle") {
            replaceLine("{applicationId}") { "applicationId = '${context.layer.namespace}'" }
        }
        context.apply("settings.gradle") {
            replaceLine("{projectName}") { "rootProject.name = '${context.layer.name}'" }
        }
    }
}
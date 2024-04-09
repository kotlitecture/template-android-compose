package kotli.template.android.compose

import kotli.engine.BaseTemplateProcessor
import kotli.engine.FeatureProvider
import kotli.engine.LayerType
import kotli.engine.TemplateState
import kotli.engine.model.LayerTypes
import kotli.engine.provider.dependencies.DependenciesUpdateProvider
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.dataflow.analytics.AnalyticsProvider
import kotli.template.android.compose.dataflow.biometric.BiometricProvider
import kotli.template.android.compose.dataflow.cache.CacheProvider
import kotli.template.android.compose.dataflow.clipboard.ClipboardProvider
import kotli.template.android.compose.dataflow.common.CommonDataFlowProvider
import kotli.template.android.compose.dataflow.config.ConfigProvider
import kotli.template.android.compose.dataflow.encryption.EncryptionProvider
import kotli.template.android.compose.dataflow.http.HttpProvider
import kotli.template.android.compose.dataflow.messaging.MessagingProvider
import kotli.template.android.compose.dataflow.network.NetworkProvider
import kotli.template.android.compose.dataflow.notifications.NotificationsProvider
import kotli.template.android.compose.dataflow.paging.PagingProvider
import kotli.template.android.compose.dataflow.database.DatabaseProvider
import kotli.template.android.compose.dataflow.encryptedkeyvalue.EncryptedKeyValueStorageProvider
import kotli.template.android.compose.dataflow.keyvalue.KeyValueStorageProvider
import kotli.template.android.compose.dataflow.work.WorkProvider
import kotli.template.android.compose.devops.distribution.DistributionProvider
import kotli.template.android.compose.devops.i18n.I18NProvider
import kotli.template.android.compose.metadata.build.BuildToolProvider
import kotli.template.android.compose.metadata.design.UiDesignSystemProvider
import kotli.template.android.compose.metadata.di.DependencyInjectionProvider
import kotli.template.android.compose.metadata.navigation.UiNavigationProvider
import kotli.template.android.compose.metadata.toolkit.UiToolkitProvider
import kotli.template.android.compose.quality.crashes.CrashesProvider
import kotli.template.android.compose.quality.performance.PerformanceProvider
import kotli.template.android.compose.quality.startup.StartupProvider
import kotli.template.android.compose.showcases.ShowcasesProvider
import kotli.template.android.compose.testing.http.HttpTestingProvider
import kotli.template.android.compose.testing.logging.LoggingProvider
import kotli.template.android.compose.testing.unit_testing.UnitTestsProvider
import kotli.template.android.compose.ui.component.UiComponentProvider
import kotli.template.android.compose.ui.container.UiContainerProvider
import kotli.template.android.compose.ui.l10n.L10NProvider
import kotli.template.android.compose.ui.navigation.UiNavigationBarProvider
import kotli.template.android.compose.ui.screen.UiScreenProvider
import kotli.template.android.compose.unspecified.UnspecifiedProvider
import kotli.template.android.compose.userflow.ads.AdsProvider
import kotli.template.android.compose.userflow.auth.AuthProvider
import kotli.template.android.compose.userflow.internet.InternetProvider
import kotli.template.android.compose.userflow.kyc.KycProvider
import kotli.template.android.compose.userflow.loader.LoaderProvider
import kotli.template.android.compose.userflow.onboarding.OnboardingProvider
import kotli.template.android.compose.userflow.passcode.PasscodeProvider
import kotli.template.android.compose.userflow.payments.PaymentsProvider
import kotli.template.android.compose.userflow.review.ReviewProvider
import kotli.template.android.compose.userflow.splash.SplashProvider
import kotli.template.android.compose.userflow.support.SupportProvider
import kotli.template.android.compose.userflow.theme.ThemeProvider
import kotli.template.android.compose.userflow.update.UpdateProvider
import kotli.template.android.compose.userflow.webtonative.WebToNativeProvider
import kotli.template.android.compose.wip.WipProvider

class AndroidComposeTemplateProcessor : BaseTemplateProcessor() {

    override fun getId(): String = ID
    override fun getType(): LayerType = LayerTypes.Android
    override fun getWebUrl(): String = "https://github.com/kotlitecture/template-android-compose"

    override fun createProviders(): List<FeatureProvider> = listOf(
        // unspecified
        UnspecifiedProvider(),

        // metadata
        UiToolkitProvider(),
        UiNavigationProvider(),
        UiDesignSystemProvider(),
        DependencyInjectionProvider(),
        BuildToolProvider(),

        // design
        L10NProvider(),

        // devops
        DistributionProvider(),
        I18NProvider(),

        // dataflow
        CommonDataFlowProvider(),
        AnalyticsProvider(),
        ConfigProvider(),
        DatabaseProvider,
        KeyValueStorageProvider,
        EncryptedKeyValueStorageProvider,
        PagingProvider,
        HttpProvider(),
        CacheProvider(),
        NetworkProvider(),
        ClipboardProvider(),
        MessagingProvider(),
        WorkProvider(),
        BiometricProvider(),
        EncryptionProvider(),
        NotificationsProvider(),

        // quality
        DependenciesUpdateProvider(),
        CrashesProvider(),
        PerformanceProvider(),
        StartupProvider(),

        // ui
        UiNavigationBarProvider(),
        UiComponentProvider(),
        UiContainerProvider(),
        UiScreenProvider(),

        // userflow
        SplashProvider(),
        ThemeProvider,
        LoaderProvider(),
        InternetProvider(),
        ReviewProvider(),
        UpdateProvider(),
        AdsProvider(),
        AuthProvider(),
        KycProvider(),
        OnboardingProvider(),
        PaymentsProvider(),
        PasscodeProvider(),
        SupportProvider(),
        WebToNativeProvider(),

        // testing
        LoggingProvider(),
        HttpTestingProvider(),
        UnitTestsProvider(),

        // showcases
        ShowcasesProvider,

        // wip
        WipProvider
    )

    override fun processBefore(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            ReplaceMarkedText(
                text = "kotli.app",
                marker = "{applicationId}",
                replacer = state.layer.namespace,
                singleLine = true
            )
        )
        state.onApplyRules(
            "app/src/main/res/values/strings.xml",
            ReplaceMarkedText(
                text = "My App",
                marker = "app_title",
                replacer = state.layer.name,
                singleLine = true
            )
        )
        state.onApplyRules(
            "settings.gradle",
            ReplaceMarkedText(
                text = "template",
                marker = "{projectName}",
                replacer = state.layer.name,
                singleLine = true
            )
        )
    }

    companion object {
        const val ID = "template-android-compose"
    }

}
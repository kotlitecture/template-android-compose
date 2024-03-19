package app.datasource.biometric

import android.app.Application
import core.data.datasource.biometric.BasicBiometricSource

/**
 * Decorator class for working with biometric.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppBiometricSource(app: Application) : BasicBiometricSource(app)
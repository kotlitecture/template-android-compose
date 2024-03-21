## Overview

The API can be accessed through:
- `core.data.datasource.biometric.BiometricSource` - facade interface at the core module level.
- `app.datasource.biometric.AppBiometricSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

Facade **BiometricSource** provides the following methods:

- `isAvailable(): Boolean` - Checks if biometric authentication is available on the device.

## Example

Both the **facade** and **decorator** are pre-configured via dependency injection (DI) as singletons in `app.di.ProvidesBiometricSource`.

To start using, just inject it to your DI managed class.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val biometricSource: BiometricSource // AppBiometricSource
) : AppViewModel() {

    override fun doBind() {
        launchAsync("isAvailable") {
            if (biometricSource.isAvailable()) {
                ...
            }
        }
    }

}
```
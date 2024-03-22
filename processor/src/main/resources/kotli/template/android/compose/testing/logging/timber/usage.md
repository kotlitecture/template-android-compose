## Overview

Library is pre-configured in the `app.datasource.logging.TimberInitializer` class to log events only for **debug** builds.

## Example

```kotlin
import timber.log.Timber

class Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Timber.d("Hello World!")
    }

}
```
## Overview

The plugin is configured to publish artifacts to App Distribution via [Gradle](https://firebase.google.com/docs/app-distribution/android/distribute-gradle?apptype=apk).

The configuration of the plugin is available in `app/build.gradle`.

Ensure that you follow all the required configurations before proceeding.

## Usage

[Official link](https://firebase.google.com/docs/app-distribution/android/distribute-gradle?apptype=apk#step_4_distribute_your_app_to_testers).

### Distribute Debug Build

```
   ./gradlew assembleDebug appDistributionUploadDebug
```

### Distribute Staging Build

```
   ./gradlew assembleStaging appDistributionUploadStaging
```

### Distribute Release Build

```
   ./gradlew assembleRelease appDistributionUploadRelease
```
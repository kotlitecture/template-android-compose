The plugin is configured to publish artifacts to App Distribution via [Gradle](https://firebase.google.com/docs/app-distribution/android/distribute-gradle?apptype=apk).

The configuration of plugin is available in `app/build.gradle`. By default, it relies on two following pre-conditions.

### Service Account JSON file is available in the root as serviceCredentialsFile.json

You can modify the destination and file location in `app/build.gradle`.

To obtain this file:

1. Go to [Firebase Console of your project](https://console.firebase.google.com/).
2. Go to **Project settings**.
3. Select **Service accounts** tab.
4. Click on **Generate new private key** button.
5. Put this file into the root of the repository as `serviceCredentialsFile.json`.

```
!!! DO NOT STORE THIS FILE IN REPOSITORY !!!
```

### Testers are configured for the Firebase Project

Better to follow this [simple and official instruction](https://firebase.google.com/docs/app-distribution/add-remove-search-testers?platform=android).

By default, the plugin is configured to distribute builds to the following groups:

1. **Debug Builds** - Group `android-developers` is required.
   ```
    ./gradlew assembleDebug appDistributionUploadDebug
    ```
2. **Staging Builds** - Group `android-testers` is required.
    ```
    ./gradlew assembleStaging appDistributionUploadStaging
    ```
3. **Release Builds** - Group `android-testers` is required.
    ```
    ./gradlew assembleRelease appDistributionUploadRelease
    ```

Once both conditions are met, execute one of the specified Gradle commands to distribute your build.

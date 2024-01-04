The plugin is already configured to automatically generate Baseline Profile when assembling a new release.
In case you want to reduce the time of your release builds, follow these steps:

1. Assembly release build
   ```
   ./gradlew :app:assembleRelease
   ```
2. Commit generated files under directory **app/src/release/generated/baselineProfiles** to VCS.
3. Find and set parameter **automaticGenerationDuringBuild** to **false** in the build file **app/build.gradle**.
4. Proceed steps 1-2 each time you add/update project dependencies to make sure the profile is up-to-date.
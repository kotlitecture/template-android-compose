The plugin is already configured for the **app** module to publish release bundles to internal track of GP.
But it is still required to properly configure Google Play Developer account.
So here the minimum steps to follow:

1. Assemble release bundle (you need exactly aab bundle)
   ```
   ./gradlew :app:bundleRelease
   ```
2. Manually upload generated bundle of your app (*app/build/outputs/bundle/release/app-release.aab*) to Google Play using the [Google Play Console](https://play.google.com/apps/publish).
   ```
   Configure your Google Play Account with Play App Signing for better security (https://developer.android.com/studio/publish/app-signing#enroll).
   ```
3. [Create a GCP project for your app](https://console.cloud.google.com)
4. Enable the [AndroidPublisher API](https://console.cloud.google.com/apis/library/androidpublisher.googleapis.com) for that GCP project.
5. Create a [service account and key](https://console.cloud.google.com/apis/credentials/serviceaccountkey)
   1. Make sure you're in the GCP project you used above (check the `project` query param in the
      URL)
   2. Select `New service account`
   3. Give it a name and the Project Owner role (don't worry, we'll remove this later)
   4. After creating the service account, find it in the list of all service accounts and use the 3-dots menu to `Manage keys`
   5. From there, create a new key using the `Add key` menu (leave JSON selected)
   6. Save downloaded JSON credentials as *app/assemble/google-play-publisher.json*
6. Give your service account [permissions to publish apps](https://play.google.com/console/developers/users-and-permissions) on your behalf
   1. Click `Invite new user`
   2. Copy/paste the service account email (you can find it in the JSON credentials)
   3. Don't touch the roles
   4. Specify which apps the service account should have access to.
   5. Specify the next minimum required permissions:
      - View app information and download bulk reports (read-only)
      - Manage testing tracks and edit tester lists
      - Manage store presence
7. Run `./gradlew app:bootstrapReleaseListing` or some other GPP task to validate your setup
8. Check [the official documentation](https://github.com/Triple-T/gradle-play-publisher?tab=readme-ov-file#publishing-an-app-bundle) how to use the plugin manually or from CI.
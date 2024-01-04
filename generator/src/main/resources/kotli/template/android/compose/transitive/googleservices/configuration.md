1. Sign in and create your project in [Firebase Console](https://console.firebase.google.com/).
2. Go to Project Overview -> Project Settings -> Add App -> Android.
3. Enter your app's package name **kotli.app** in the Android package name field.
4. Click **Register app**.
5. Repeat steps 2-4 with package name **kotli.app.debug** for debug builds.
6. Repeat steps 2-4 with package name **kotli.app.staging** for staging builds.
7. In the center of the project overview page click the button **google-services.json**.
8. Replace file **google-services.json** in the module **app** with file from previous step.
9. Launch app on the device to make sure everything works (it is important that newly created google-services.json has all three mentioned packages).
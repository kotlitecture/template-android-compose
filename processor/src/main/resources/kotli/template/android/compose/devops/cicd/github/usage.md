## Overview

All actions are pre-configured and available under the folder `.github/workflows`.

Whenever you make any changes in the **GitHub Repository** with the project, one of the actions will be triggered, depending on the branch.

### run-unit-tests.yml

Triggered whenever a commit is made to any branch except: `debug`, `staging`, `release`.

The workflow is configured to run tests using the Gradle command:

```
./gradlew signingReport testDebugUnitTest
```

### build-debug.yml

Triggered whenever a commit is made to the branch `debug`.

The workflow is configured to run tests and publish the `debug` build on GitHub or other distribution channels if included in the project.

### build-staging.yml

Triggered whenever a commit is made to the branch `staging`.

The workflow is configured to run tests and publish the `staging` build on GitHub or other distribution channels if included in the project.

### build-release.yml

Triggered whenever a commit is made to the branch `release`.

The workflow is configured to run tests and publish the `release` build on GitHub or other distribution channels if included in the project.
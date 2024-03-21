## Overview

Component package: `app.userflow.review.google`

The component is pre-configured and provides data
source `app.userflow.review.google.GoogleReviewSource` with the following methods:

- `review(debug: Boolean): ReviewData` - Initiates the review process based on the specified debug mode.

## Example

To start using, just inject it to your DI managed class and call `review` method when the flow need to be run.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val googleReviewSource: GoogleReviewSource
) : AppViewModel() {

    fun onReview() {
        launchAsync("review") {
            googleReviewSource.review(false)
        }
    }

}
```

## Test

In-app reviews require your app to be published on the Play Store, at least in the internal track.
If you only need to test the behavior of the API, pass the parameter **true** to the `review` method
to simulate the behavior.

For more details, refer to the documentation
here: [In-app review testing](https://developer.android.com/guide/playcore/in-app-review/test).
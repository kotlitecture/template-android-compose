## Overview

Component: `app/ui/container/FixedTopBarLayout.kt`

## Example

```kotlin
@Composable
fun TemplateScreen(data: TemplateDestination.Data?) {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    FixedTopBarLayout(
        title = "MyScreen",
        onBack = viewModel::onBack,
        actions = {
                  
        },
        content = {

        }
    )
}
```
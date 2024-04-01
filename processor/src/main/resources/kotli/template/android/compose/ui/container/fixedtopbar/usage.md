## Overview

Component: `app/ui/container/FixedTopBarLayout.kt`

## Example

```kotlin
@Composable
fun TemplateScreen() {
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
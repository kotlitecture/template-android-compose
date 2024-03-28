## Overview

Component: `app/ui/container/FixedHeaderFooterLayout.kt`

## Example

```kotlin
@Composable
fun TemplateScreen(data: TemplateDestination.Data?) {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    FixedHeaderFooterLayout(
        header = {

        },
        footer = {

        },
        content = {
            
        }
    )
}
```
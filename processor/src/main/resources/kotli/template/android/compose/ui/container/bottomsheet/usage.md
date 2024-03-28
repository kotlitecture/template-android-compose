## Overview

Component: `app/ui/container/BottomSheetLayout.kt`

## Example

```kotlin
@Composable
fun TemplateScreen(data: TemplateDestination.Data?) {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    if (viewModel.bottomSheetStore.asStateValueNotNull()) {
        BottomSheetLayout(
            onDismissRequest = viewModel.bottomSheetStore::clear,
            content = {
                // content
            }
        )
    }
}
```
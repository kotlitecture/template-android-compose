## Overview

Component: `app/ui/container/BottomSheetLayout.kt`

## Example

```kotlin
@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    val bottomSheetStore = viewModel.bottomSheetStore
    if (bottomSheetStore.asStateValueNotNull()) {
        BottomSheetLayout(
            onDismissRequest = bottomSheetStore::clear,
            content = {
                // content
            }
        )
    }
}
```
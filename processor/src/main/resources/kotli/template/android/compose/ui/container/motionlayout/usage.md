## Overview

Component: `app/ui/container/MotionLayout.kt`

The component is simply a wrapper for `androidx.constraintlayout.compose.MotionLayout`, allowing you to use it interchangeably.

**Motion Layout** behavior can be configured using the [JSON schema](https://github.com/androidx/constraintlayout/wiki/Compose-MotionLayout-JSON-Syntax).

## Example

```kotlin
@Composable
fun TemplateScreen() {
    MotionLayout(
        sceneRes = R.raw.scheme,
        progress = mutableStateOf(0f),
        content = {
            
        }
    )
}
```
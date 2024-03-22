# Kotli Template of Android Compose Application

| Resource                | Link                                         |
|-------------------------|----------------------------------------------|
| Usage from a web portal | [https://kotlitecture.com/project](https://kotlitecture.com/project) |
| Usage Guide             | [https://kotlitecture.github.io/usage](https://kotlitecture.github.io/usage) |
| About **Kotli**         | [https://kotlitecture.github.io](https://kotlitecture.github.io) |

## Overview

This is a foundational [Kotli](https://kotlitecture.github.io) template for Android app development centered around **Android Jetpack Compose**.

In this template, all app-specific logic resides within the `app` module, while common logic is included within `core` modules.

As this is an Android project, the default package remains `app` and is not altered to match the layer namespace during template generation.
Instead, the namespace is utilized as the `applicationId`. This can be changed or made configurable with time.

In its current implementation, the template is based on the following pre-configured integrations:

| Library               | Documentation                                                                     |
|-----------------------|-----------------------------------------------------------------------------------|
| Jetpack Compose       | [Link](https://developer.android.com/jetpack/compose)                             |
| Jetpack Navigation    | [Link](https://developer.android.com/guide/navigation)                            |
| Hilt                  | [Link](https://dagger.dev/hilt/)                                                  |
| Material Components 3 | [Link](https://developer.android.com/jetpack/androidx/releases/compose-material3) |

```
This selection can be modified and made customizable once any alternative solutions are integrated into the template.
```

## Current template state

The template is stable and can be used for any purpose. More features will be added over time to accelerate the development process and customize project architectures for specific needs.
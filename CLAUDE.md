# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

Requires Android Studio or Android SDK command-line tools.

**Via Android Studio (recommended):** Open the project root → wait for Gradle sync → press Run (Shift+F10).

**Debug APK via Gradle wrapper (from project root):**
```
.\gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

**Release APK:**
```
.\gradlew assembleRelease
```

**Unit tests (JVM, no device needed):**
```
.\gradlew test
```

**Instrumented tests (requires connected device or emulator):**
```
.\gradlew connectedAndroidTest
```

**Run a single unit test class:**
```
.\gradlew test --tests "com.jenstine.travelbuddy.ExampleUnitTest"
```

## Architecture

TravelBuddy is a standard Jetpack Compose Android app (minSdk 24, targetSdk 36, Kotlin).

- **`MainActivity.kt`** — sole Activity; uses `setContent` to host the Compose UI tree inside `TravelBuddyTheme > Scaffold`
- **`ui/theme/`** — Material3 theming: `Color.kt` defines the palette, `Type.kt` defines typography, `Theme.kt` wires them into `TravelBuddyTheme` (supports dynamic color on Android 12+, dark/light mode)

The project is a fresh template with no app-specific screens or navigation yet. New features go in `app/src/main/java/com/jenstine/travelbuddy/`; new screens should be added as `@Composable` functions and wired into `MainActivity` or a nav graph.

## Key Config

- **App ID / package:** `com.jenstine.travelbuddy`
- **Version:** `versionCode = 1`, `versionName = "1.0"` in `app/build.gradle.kts`
- **Minification:** disabled (`isMinifyEnabled = false`) — enable and configure `proguard-rules.pro` before a Play Store release
- **Compile SDK:** 36 (with `minorApiLevel = 1`)

# Implementation Plan - Fix KSP "unexpected jvm signature V" Error

The error `[ksp] java.lang.IllegalStateException: unexpected jvm signature V` is a known issue when using Room with KSP on newer Kotlin versions. It occurs when KSP fails to correctly process `suspend` functions that return `Unit` (represented as `V` in JVM signatures) due to incompatibilities between Room's KSP processor and the Kotlin compiler/KSP plugin versions.

## User Review Required

> [!IMPORTANT]
> I am upgrading Room from `2.6.1` to `2.8.4`. While Room usually maintains backward compatibility, this is a significant version jump. I will also clean the build to ensure the new KSP processor runs on a fresh state.

## Proposed Changes

### 1. Build Configuration Updates

#### [MODIFY] [libs.versions.toml](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/gradle/libs.versions.toml)
* Update `room` version to `2.8.4`.

#### [MODIFY] [build.gradle.kts](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/app/build.gradle.kts)
* Replace hardcoded Room version `2.6.1` with the version catalog reference `libs.versions.room.get()`.
* Use the library references from the version catalog for `room-runtime`, `room-ktx`, and `room-compiler`.

### 2. Cleanup

* Execute `./gradlew clean` to remove any corrupt KSP generated files.

## Verification Plan

### Automated Tests
* Run `./gradlew :app:kspDebugKotlin` to verify the KSP processing error is gone.
* Run `./gradlew assembleDebug` to ensure the project builds successfully.

### Manual Verification
* None required beyond the build success.

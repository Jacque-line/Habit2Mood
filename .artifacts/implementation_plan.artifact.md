# Implementation Plan - Fix Dependency Version and SDK Mismatch

The project is failing to build because `androidx.core:core-ktx:1.19.0` (referenced as `libs.core.ktx`) requires `compileSdk 37` and Android Gradle Plugin (AGP) 9.1.0+, while the project is currently configured with `compileSdk 35` and AGP 8.7.3.

There is a redundant dependency for `core-ktx` in the project:
1. `libs.androidx.core.ktx` pointing to version `1.15.0` (Compatible with SDK 35).
2. `libs.core.ktx` pointing to version `1.19.0` (Incompatible with current SDK/AGP).

## Proposed Changes

### Build Configuration

#### [MODIFY] [app/build.gradle.kts](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/app/build.gradle.kts)
- Remove the redundant and problematic `libs.core.ktx` dependency.
- Retain `libs.androidx.core.ktx` which uses a version compatible with `compileSdk 35`.

#### [MODIFY] [libs.versions.toml](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/gradle/libs.versions.toml)
- Remove the unused and problematic `coreKtxVersion = "1.19.0"` and the corresponding `core-ktx` library entry to prevent future issues and confusion.

## Verification Plan

### Automated Tests
- Perform a Gradle Sync.
- Run a build: `./gradlew :app:assembleDebug` or `./gradlew :app:processDebugResources` to ensure the resource linking and dependency resolution succeed.

### Manual Verification
- Verify that the IDE no longer reports version mismatch errors in the build files.

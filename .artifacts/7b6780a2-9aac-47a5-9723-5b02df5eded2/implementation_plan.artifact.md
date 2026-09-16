# Fix Android Resource Linking Error

The project is failing to build because it references `Theme.Material3.DayNight.NoActionBar` in `themes.xml`, but the necessary Material Components for Android library (`com.google.android.material:material`) is not included in the project's dependencies.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/gradle/libs.versions.toml)
- Add the `material` library version and definition.

#### [MODIFY] [build.gradle.kts (app)](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/app/build.gradle.kts)
- Add `libs.material` as an implementation dependency.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:processDebugResources` to verify that resource linking succeeds.
- Run a full build using `./gradlew assembleDebug`.

### Manual Verification
- Deploy the app to a device/emulator to ensure the theme is correctly applied.

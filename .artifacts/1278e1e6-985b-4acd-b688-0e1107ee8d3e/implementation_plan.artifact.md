# Fix Android Resource Linking Error (Material3 Theme Not Found)

The project is failing to build because it references `Theme.Material3.DayNight.NoActionBar` in `app/src/main/res/values/themes.xml`, but the `com.google.android.material:material` library is not included in the project dependencies. This library is required for Material 3 XML themes.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/gradle/libs.versions.toml)
- Add `material` version (1.14.0).
- Add `google-material` library definition.

#### [MODIFY] [build.gradle.kts](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/app/build.gradle.kts)
- Add `implementation(libs.google.material)` to the dependencies block.

### Resources

#### [MODIFY] [themes.xml](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/app/src/main/res/values/themes.xml)
- Ensure the `Theme.Habit2Mood` style inherits from `Theme.Material3.DayNight.NoActionBar` (this is already partially done in the attempt to reproduce).

## Verification Plan

### Automated Tests
- Run `./gradlew :app:processDebugResources` to verify that resource linking succeeds.
- Run Gradle Sync to ensure dependencies are correctly resolved.

### Manual Verification
- Verify that the app builds and runs without the resource linking error.

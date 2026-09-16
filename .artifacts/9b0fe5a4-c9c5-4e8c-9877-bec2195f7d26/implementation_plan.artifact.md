# Fix Room Implementation Not Found Error

The app crashes at startup because Room cannot find the generated implementation class `AppDatabase_Impl`. This is caused by using `annotationProcessor` in a Kotlin project, which doesn't correctly process Kotlin annotations. I will migrate to KSP (Kotlin Symbol Processing), which is the recommended and modern way to handle Room annotations in Kotlin.

## User Review Required

> [!IMPORTANT]
> The build process will require a Gradle Sync and a full build after these changes to generate the Room implementation classes.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/gradle/libs.versions.toml)
- Add KSP plugin definition to the `[plugins]` section.

#### [MODIFY] [build.gradle.kts](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/build.gradle.kts) (root)
- Apply the KSP plugin in the top-level build file.

#### [MODIFY] [build.gradle.kts](file:///C:/xampp/htdocs/miscindytugas/Habit2Mood/app/build.gradle.kts) (app)
- Apply the KSP plugin.
- Replace `annotationProcessor(libs.androidx.room.compiler)` with `ksp(libs.androidx.room.compiler)`.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to ensure the project builds correctly and the Room implementation is generated.

### Manual Verification
- Deploy the app to a device or emulator and verify that it starts without the `RuntimeException`.

# D8 Dex Merging Tool

Allows you to merge multiple dex files using D8. Supports multidex and other options.

This code has been extracted from [Android Studio](https://cs.android.com/android-studio/platform/tools/base)
as a standalone tool to merge dex files using D8.

Commit Hash: `4a71eee69871580140f3ad232ae6def06562185c` (2025-03-03 17:50)

Few modifications have been made so that it can be run on Android aswell.


## Available through Jitpack

In root settings.gradle at the end of repositories

```kt
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

In your project build.gradle
```kt
dependencies {
    implementation("com.github.PranavPurwar:D8Merger:cfae90406c")
}
```

# Usage

```kt
import dev.pranav.d8.merger.DexMerger;

DexMerger.merge(
    dexFiles = listOf(File("classes.dex"), File("classes2.dex")),
    outputDir = File("/path/to/outputDir")
)
```

View [this file](https://github.com/PranavPurwar/D8Merger/blob/9c2f00ba3f2585d58b4ffc0004d32fa2f68487a1/src/main/kotlin/dev/pranav/d8/merger/DexMerger.kt#L1) for information about the rest of available options.

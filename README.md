# Standalone D8 merging tool

This code has been extracted from [Android Studio](https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/builder-r8/;bpv=1)
as a standalone tool to merge dex files using D8.

Commit Hash: 4a71eee69871580140f3ad232ae6def06562185c (2025-03-03 17:50)

# Usage

```kt
// The dex files to merge
val toMerge = listOf(
    File("classes.dex"),
    File("classes2.dex")
)

// Create a list of DexArchiveEntry from the files to merge
val entries = toMerge.map { file ->
    DexArchiveEntry(
        file.readBytes(),
        file.name,
        DexArchives.fromInput(file.toPath())
    )
}

/**
 * A message receiver.
 *
 * {@link MessageReceiver}s receive build {@link Message}s and either
 * <ul><li>Output them to a logging system</li>
 * <li>Output them to a user interface</li>
 * <li>Transform them, such as mapping from intermediate files back to source files</li></ul>
 */
val messageReceiver = MessageReceiver { message -> println(message.kind.name + ": " + message.text) }

/**
 * Creates an instance of dex archive merger that is using d8 to merge dex files.
 *
 *
 * <p>Dexing type specifies how files will be merged:
 *
 * <ul>
 *   <li>if it is {@link DexingType#MONO_DEX}, a single dex file is written, named classes.dex
 *   <li>if it is {@link DexingType#LEGACY_MULTIDEX}, there can be more than 1 dex files. Files
 *       are named `classes.dex`, `classes2.dex`, `classes3.dex` etc. In this mode, path to a file
 *       containing the list of classes to be placed in the main dex file must be specified.
 *   <li>if it is {@link DexingType#NATIVE_MULTIDEX}, there can be 1 or more dex files.
 * </ul>
 */

val merger = DexArchiveMerger.createD8DexMerger(
    messageReceiver,
    DexingType.NATIVE_MULTIDEX,
    26, // minSdkVersion
    false, // whether the build is debuggable
    Executors.newCachedThreadPool()
)

/**
 * Merges the specified dex archive entries into one or more dex files under the specified
 * directory.
 *
 * dexArchiveEntries the dex archive entries to merge
 * outputDir directory where merged dex file(s) will be written, must exist
 * globalSynthetics the global synthetics files to be merged with or without dex archive
 *     entries
 * mainDexRulesFiles files containing the Proguard rules
 * mainDexRules Proguard rules written as strings
 * userMultidexKeepFile a user specified file containing classes to be kept in the main
 *     dex list
 * libraryFiles classes that are used only to resolve types in the program classes, but
 *     are not packaged in the final binary e.g. android.jar, provided classes etc.
 * mainDexListOutput the output location of classes to be kept in the main dex file
 * inputProfileForDexStartupOptimization the merged startup profile that is used to
 *     optimize the dex in D8 when present
 */
merger.mergeDexArchives(
    entries, // the dex archive entries to merge
    listOf<Path>(), // the global synthetics files to be merged with or without dex archive entries
    Path("output/"), // directory where merged dex file(s) will be written, must exist
    null, // files containing the Proguard rules
    null, // Proguard rules written as strings
    null, // a user specified file containing classes to be kept in the main dex list
    null, // classes that are used only to resolve types in the program classes, but are not packaged in the final binary e.g. android.jar, provided classes etc.
    null, // inputProfileForDexStartupOptimization the merged startup profile that is used to optimize the dex in D8 when present
    null, // the output location of classes to be kept in the main dex file
    null // file to write the d8 build metadata
)
```
package dev.pranav.d8.merger

import com.android.builder.dexing.DexArchiveEntry
import com.android.builder.dexing.DexArchiveMerger
import com.android.builder.dexing.DexingType
import com.android.builder.dexing.DexArchives
import com.android.ide.common.blame.MessageReceiver
import java.io.File
import java.nio.file.Path
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Utility for merging dex files using D8 with minimal setup.
 *
 * This class wraps the D8 dex merging process, allowing to merge dex files with a single simple method call.
 *
 * Example usage:
 * ```kotlin
 * DexMerger.merge(
 *     dexFiles = listOf(File("classes.dex"), File("classes2.dex")),
 *     outputDir = File("/path/to/outputDir")
 * )
 * ```
 *
 * @param dexFiles List of .dex files to merge.
 * @param outputDir Directory where merged dex file(s) will be written. Must exist.
 * @param dexingType Dexing type (Mono, Legacy Multidex, Native Multidex). Default: Native Multidex.
 * @param minSdkVersion Minimum SDK version. Default: 21.
 * @param isDebuggable Whether the build is debuggable. Default: false.
 * @param globalSynthetics Global synthetics files to merge. Default: empty.
 * @param mainDexRulesFiles Files containing Proguard rules. Optional.
 * @param mainDexRules Proguard rules as strings. Optional.
 * @param userMultidexKeepFile File containing classes to keep in main dex. Optional.
 * @param libraryFiles Classes used for type resolution only (e.g., android.jar). Optional.
 * @param mainDexListOutput Output location for main dex class list. Optional.
 * @param inputProfileForDexStartupOptimization Startup profile for D8 optimization. Optional.
 * @param d8Metadata File to write D8 build metadata. Optional.
 * @param messageReceiver Receives build messages. Default: prints to stdout.
 * @param executor ExecutorService for D8. Default: newCachedThreadPool().
 *
 * @throws DexArchiveMergerException If merging fails.
 * @throws IOException If file operations fail.
 */
object DexMerger {
    @JvmStatic
    fun merge(
        dexFiles: List<File>,
        outputDir: File,
        dexingType: DexingType = DexingType.NATIVE_MULTIDEX,
        minSdkVersion: Int = 21,
        isDebuggable: Boolean = false,
        globalSynthetics: List<Path> = emptyList(),
        mainDexRulesFiles: List<Path>? = null,
        mainDexRules: List<String>? = null,
        userMultidexKeepFile: Path? = null,
        libraryFiles: Collection<Path>? = null,
        mainDexListOutput: Path? = null,
        inputProfileForDexStartupOptimization: Path? = null,
        d8Metadata: Path? = null,
        messageReceiver: MessageReceiver = MessageReceiver { message -> println(message.kind.name + ": " + message.text) },
        executor: ExecutorService = Executors.newCachedThreadPool()
    ) {
        val entries = dexFiles.map { file ->
            DexArchiveEntry(
                file.readBytes(),
                file.name,
                DexArchives.fromInput(file.toPath())
            )
        }
        val merger = DexArchiveMerger.createD8DexMerger(
            messageReceiver,
            dexingType,
            minSdkVersion,
            isDebuggable,
            executor
        )
        merger.mergeDexArchives(
            entries,
            globalSynthetics,
            outputDir.toPath(),
            mainDexRulesFiles,
            mainDexRules,
            userMultidexKeepFile,
            libraryFiles,
            inputProfileForDexStartupOptimization,
            mainDexListOutput,
            d8Metadata
        )
    }
}
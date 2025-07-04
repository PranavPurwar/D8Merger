package com.android.builder.dexing;

import com.android.ide.common.blame.MessageReceiver;
import com.android.tools.r8.CompilationMode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * A dex archive merger that can merge dex files from multiple dex archives into one or more dex
 * files.
 */
public interface DexArchiveMerger {

    /** Creates an instance of dex archive merger that is using d8 to merge dex files. */
        static DexArchiveMerger createD8DexMerger(
            MessageReceiver messageReceiver,
            DexingType dexingType,
            int minSdkVersion,
            boolean isDebuggable,
            ExecutorService d8ExecutorService) {
        return new D8DexArchiveMerger(
                messageReceiver,
                dexingType,
                minSdkVersion,
                isDebuggable ? CompilationMode.DEBUG : CompilationMode.RELEASE,
                d8ExecutorService);
    }

    /**
     * Merges the specified dex archive entries into one or more dex files under the specified
     * directory.
     *
     * <p>Dexing type specifies how files will be merged:
     *
     * <ul>
     *   <li>if it is {@link DexingType#MONO_DEX}, a single dex file is written, named classes.dex
     *   <li>if it is {@link DexingType#LEGACY_MULTIDEX}, there can be more than 1 dex files. Files
     *       are named classes.dex, classes2.dex, classes3.dex etc. In this mode, path to a file
     *       containing the list of classes to be placed in the main dex file must be specified.
     *   <li>if it is {@link DexingType#NATIVE_MULTIDEX}, there can be 1 or more dex files.
     * </ul>
     *
     * @param dexArchiveEntries the dex archive entries to merge
     * @param outputDir directory where merged dex file(s) will be written, must exist
     * @param globalSynthetics the global synthetics files to be merged with or without dex archive
     *     entries
     * @param mainDexRulesFiles files containing the Proguard rules
     * @param mainDexRules Proguard rules written as strings
     * @param userMultidexKeepFile a user specified file containing classes to be kept in the main
     *     dex list
     * @param libraryFiles classes that are used only to resolve types in the program classes, but
     *     are not packaged in the final binary e.g. android.jar, provided classes etc.
     * @param mainDexListOutput the output location of classes to be kept in the main dex file
     * @param inputProfileForDexStartupOptimization the merged startup profile that is used to
     *     optimize the dex in D8 when present
     */
    void mergeDexArchives(
            List<DexArchiveEntry> dexArchiveEntries,
            List<Path> globalSynthetics,
            Path outputDir,
            List<Path> mainDexRulesFiles,
            List<String> mainDexRules,
            Path userMultidexKeepFile,
            Collection<Path> libraryFiles,
            Path inputProfileForDexStartupOptimization,
            Path mainDexListOutput,
            Path d8Metadata)
            throws DexArchiveMergerException, IOException;
}

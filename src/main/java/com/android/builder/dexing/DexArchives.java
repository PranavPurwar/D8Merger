/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.builder.dexing;

import com.android.annotations.NonNull;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/** Helper methods for the {@link DexArchive}. */
public final class DexArchives {

    private DexArchives() {}

    /**
     * Creates a {@link DexArchive} from the specified path. It supports
     * .jar files and directories as inputs.
     *
     * <p>In case of a .jar file, note there are two mutually exclusive modes, write-only and
     * read-only. In case of a write-only mode, only allowed operation is adding entries. If
     * read-only mode is used, entires can only be read.
     */
        public static DexArchive fromInput(Path path) throws IOException {
        if (ClassFileInputs.jarMatcher.matches(path)) {
            return new NonIncrementalJarDexArchive(path);
        } else {
            return new DirDexArchive(path);
        }
    }

        static List<DexArchiveEntry> getEntriesFromSingleArchive(Path archivePath)
            throws IOException {
        try (DexArchive archive = fromInput(archivePath)) {
            return archive.getSortedDexArchiveEntries();
        }
    }

        static List<DexArchiveEntry> getAllEntriesFromArchives(Collection<Path> inputs)
            throws IOException {
        List<DexArchiveEntry> entries = Lists.newArrayList();
        for (Path p : inputs) {
            entries.addAll(getEntriesFromSingleArchive(p));
        }
        return entries;
    }
}

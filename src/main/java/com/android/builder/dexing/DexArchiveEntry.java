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

import com.android.SdkConstants;
import com.android.annotations.NonNull;

import com.google.common.base.Preconditions;

/**
 * A single DEX file in a dex archive. It is uniquely identified with {@link #relativePathInArchive}
 * within a single {@link DexArchive}. It also contains the DEX file's content ({@link
 * #dexFileContent}).
 */
public final class DexArchiveEntry {

    private final byte[] dexFileContent;
    private final String relativePathInArchive;
    private final DexArchive dexArchive;

    public DexArchiveEntry(
            byte[] dexFileContent,
            String relativePathInArchive,
            DexArchive dexArchive) {
        this.relativePathInArchive = relativePathInArchive;
        this.dexFileContent = dexFileContent;
        this.dexArchive = dexArchive;
    }

    /**
     * Takes the specified .dex file, and changes its extension to .class. It fails if invoked with
     * a file name that does not end in .dex.
     */
        public static String withClassExtension(String dexEntryPath) {
        Preconditions.checkState(
                dexEntryPath.endsWith(SdkConstants.DOT_DEX),
                "Dex archives: setting .CLASS extension only for .DEX files");

        return dexEntryPath.substring(0, dexEntryPath.length() - SdkConstants.DOT_DEX.length())
                + SdkConstants.DOT_CLASS;
    }

    /** Returns content of this DEX file. */
        public byte[] getDexFileContent() {
        return dexFileContent;
    }

    /**
     * Returns a path relative to the root path of the dex archive containing it.
     *
     * @return relative path of this entry from the root of the dex archive
     */
        public String getRelativePathInArchive() {
        return relativePathInArchive;
    }

        public DexArchive getDexArchive() {
        return dexArchive;
    }
}

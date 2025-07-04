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

package com.android.builder.dexing.r8;

import com.android.tools.r8.ArchiveClassFileProvider;
import com.android.tools.r8.ProgramResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

class CachingArchiveClassFileProvider extends ArchiveClassFileProvider {


    private ConcurrentHashMap<String, ProgramResource> resources = new ConcurrentHashMap<>();

    public CachingArchiveClassFileProvider(Path archive) throws IOException {
        super(archive);
    }


    @Override
    public ProgramResource getProgramResource(String descriptor) {
        return resources.computeIfAbsent(descriptor, desc -> super.getProgramResource(desc));
    }
}

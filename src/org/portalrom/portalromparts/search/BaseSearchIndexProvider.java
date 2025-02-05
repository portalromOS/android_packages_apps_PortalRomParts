/*
 * Copyright (C) 2022 The Portal Project
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
package org.portalrom.portalromparts.search;

import android.content.Context;

import java.util.List;
import java.util.Set;

/**
 * Convenience class which can be used to return additional search metadata without
 * having to implement all methods.
 */
public class BaseSearchIndexProvider implements Searchable.SearchIndexProvider {

    @Override
    public List<SearchIndexableRaw> getRawDataToIndex(Context context) {
        return null;
    }

    @Override
    public Set<String> getNonIndexableKeys(Context context) {
        return null;
    }
}

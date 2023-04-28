/*
 * Copyright (c) 2023 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.sync.api.engine

interface SyncEngine {

    /**
     * Entry point to the Sync Engine
     * This will be used by Background Sync and App Triggered workers
     */
    fun syncNow(trigger: SyncTrigger)

    enum class SyncTrigger {
        BACKGROUND_SYNC,
        FEATURE_READ,
        ACCOUNT_CREATION,
        ACCOUNT_LOGIN
    }

    /**
     * Entry point to the Sync Engine
     * This will be triggered by Observers when data has changed
     * Add / Update / Delete operations of [SyncableType]
     *
     */
    fun notifyDataChanged()
}
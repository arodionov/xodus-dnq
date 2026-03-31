/**
 * Copyright 2006 - 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.exodus.entitystore.youtrackdb

import com.jetbrains.youtrackdb.internal.core.db.DatabaseSessionEmbedded
import com.jetbrains.youtrackdb.internal.core.gremlin.YTDBGraph

interface YTDBDatabaseProvider {
    val databaseLocation: String

    /**
     * Creates a new [YTDBGraph] instance backed by the same session pool.
     * Each graph has its own thread-local state, so it can host an independent transaction
     * on the same thread as another graph instance.
     *
     * The returned graph must NOT be closed — [com.jetbrains.youtrackdb.internal.core.gremlin.YTDBGraphEmbedded.close]
     * would close the shared pool. The session is cleaned up automatically when the transaction commits or aborts.
     */
    fun createGraph(): YTDBGraph

    fun <R> withSession(block: (DatabaseSessionEmbedded) -> R): R

    /**
     * Database-wise read-only mode.
     * Always false by default.
     */
    var readOnly: Boolean

    // is it even needed?
    val isOpen: Boolean

    fun close()
}


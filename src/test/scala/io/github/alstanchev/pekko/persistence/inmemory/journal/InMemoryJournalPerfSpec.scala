/*
 * Copyright 2016 Dennis Vriend
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

package io.github.alstanchev.pekko.persistence.inmemory.journal

import org.apache.pekko.persistence.CapabilityFlag
import org.apache.pekko.persistence.journal.JournalPerfSpec
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

class InMemoryJournalPerfSpec extends JournalPerfSpec(ConfigFactory.load("application.conf")) {
  override protected def supportsRejectingNonSerializableObjects: CapabilityFlag = true

  // TCK capability added in Pekko 2.0: replay windows whose bounds touch a deleted prefix
  override protected def supportsReplayWindowSpanningDeletedPrefix: CapabilityFlag = true

  // 10k events per measurement take well under a second in memory; keep a generous margin for slow CI runners
  // but fail fast instead of hanging the build (this was 60 minutes when the spec was disabled)
  override def awaitDurationMillis: Long = 1.minute.toMillis
}

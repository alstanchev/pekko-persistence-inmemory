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

package io.github.alstanchev.pekko.persistence.inmemory

import io.github.alstanchev.pekko.persistence.inmemory.util.UUIDs
import org.apache.pekko.persistence.PersistentRepr
import org.apache.pekko.persistence.query.TimeBasedUUID

import java.util.UUID
import scala.collection.immutable._

type Seq[A] = scala.collection.immutable.Seq[A]

def now: Long = java.lang.System.currentTimeMillis()
def nowUuid: UUID = UUIDs.timeBased()
def getTimeBasedUUID: TimeBasedUUID = TimeBasedUUID(nowUuid)

final case class JournalEntry(persistenceId: String, sequenceNr: Long, serialized: Array[Byte], repr: PersistentRepr, tags: Set[String], deleted: Boolean = false, ordering: Long = -1, timestamp: TimeBasedUUID = getTimeBasedUUID, offset: Option[Long] = None)
final case class SnapshotEntry(persistenceId: String, sequenceNumber: Long, created: Long, snapshot: Array[Byte])

given seqToVector[A]: Conversion[Seq[A], Vector[A]] with
  def apply(xs: Seq[A]): Vector[A] = xs.toVector

given setToVector[A]: Conversion[Set[A], Vector[A]] with
  def apply(xs: Set[A]): Vector[A] = xs.toVector

given mapSeqToVector[K, V]: Conversion[Map[K, Seq[V]], Map[K, Vector[V]]] with
  def apply(map: Map[K, Seq[V]]): Map[K, Vector[V]] = map.view.mapValues(_.toVector).toMap

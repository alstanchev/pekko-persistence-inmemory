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

package io.github.alstanchev.pekko.persistence.inmemory.query

import org.apache.pekko.persistence.inmemory.query.QueryTestSpec
import org.apache.pekko.stream.scaladsl.Sink

import scala.concurrent.duration._

class QueryPerfSpec extends QueryTestSpec {
  // 100k events take a few seconds in memory; fail fast instead of waiting for the inherited 60 minute defaults
  override implicit val pc: PatienceConfig = PatienceConfig(timeout = 2.minutes, interval = 300.millis)
  override def writeTimeout: FiniteDuration = 2.minutes

  def writeToJournal(numberOfEvents: Int): Unit = {
    val start = java.lang.System.currentTimeMillis()
    persist(1, numberOfEvents, "pid", "foo")
    val end = java.lang.System.currentTimeMillis()
    info(s"Writing $numberOfEvents events to the journal took: ${end - start} ms")
  }

  def allEventsFromJournal: Seq[Any] = {
    val start = java.lang.System.currentTimeMillis()
    val xs = defaultReadJournal.currentEventsByPersistenceId("pid", 0, Long.MaxValue)
      .runWith(Sink.seq).futureValue
    val end = java.lang.System.currentTimeMillis()
    info(s"currrentEventsByPersistenceId for ${xs.size} events took: ${end - start} ms")
    xs
  }

  def eventsFromJournal(numberOfEvents: Int): Seq[Any] = {
    val start = java.lang.System.currentTimeMillis()
    val xs = defaultReadJournal.eventsByPersistenceId("pid", 0, Long.MaxValue)
      .take(numberOfEvents)
      .runWith(Sink.seq).futureValue
    val end = java.lang.System.currentTimeMillis()
    info(s"eventsByPersistenceId for ${xs.size} events took: ${end - start} ms")
    xs
  }

  it should "write and query (load) events from the journal" in {
    val numberOfEvents = 100000
    writeToJournal(numberOfEvents)
    allEventsFromJournal.size shouldBe numberOfEvents
    eventsFromJournal(numberOfEvents).size shouldBe numberOfEvents
  }
}

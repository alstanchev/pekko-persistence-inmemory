package io.github.alstanchev.pekko.persistence.inmemory.query

import io.github.alstanchev.pekko.persistence.inmemory.TestSpec

import java.util.UUID

import scala.concurrent.Future

class ConcurrentUUIDTest extends TestSpec {
  def getNow(x: Any): Future[UUID] = Future(io.github.alstanchev.pekko.persistence.inmemory.nowUuid)
  it should "get unique uuids concurrently" in {
    val uuids = Future.sequence((1 to 1000).map(getNow)).futureValue
    uuids.distinct.size shouldBe uuids.size
  }
}

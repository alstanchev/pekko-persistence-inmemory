package io.github.alstanchev.pekko.persistence.inmemory;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Status;
import org.apache.pekko.testkit.TestProbe;
import org.apache.pekko.testkit.javadsl.TestKit;

import io.github.alstanchev.pekko.persistence.inmemory.extension.InMemoryJournalStorage;
import io.github.alstanchev.pekko.persistence.inmemory.extension.InMemorySnapshotStorage;
import io.github.alstanchev.pekko.persistence.inmemory.extension.StorageExtension;
import io.github.alstanchev.pekko.persistence.inmemory.extension.StorageExtensionProvider;

import org.junit.Test;

/**
 * Runs the "Clearing Journal and Snapshot messages" Java example from the README against the real storage actors.
 */
public class StorageExtensionJavaApiTest {

    @Test
    public void clearsJournalAndSnapshotStorageFromJava() {
        ActorSystem system = ActorSystem.create();
        try {
            TestProbe tp = new TestProbe(system);
            StorageExtension extension = StorageExtensionProvider.get(system);

            ActorRef journal = extension.journalStorage(system.settings().config());
            tp.send(journal, InMemoryJournalStorage.clearJournal());
            tp.expectMsg(new Status.Success(""));

            ActorRef snapshots = extension.snapshotStorage(system.settings().config());
            tp.send(snapshots, InMemorySnapshotStorage.clearSnapshots());
            tp.expectMsg(new Status.Success(""));
        } finally {
            TestKit.shutdownActorSystem(system);
        }
    }
}

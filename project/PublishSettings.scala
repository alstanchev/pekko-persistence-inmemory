import sbt._
import sbt.Keys._

/**
 * Publishing to Maven Central via the Sonatype Central Portal (https://central.sonatype.com).
 *
 * The legacy OSSRH endpoints (s01.oss.sonatype.org) were shut down by Sonatype on 2025-06-30.
 * sbt >= 1.11 has built-in Central Portal support:
 *  - releases are staged locally with `publishSigned` (publishTo = localStaging) and then uploaded
 *    with `sonaRelease` (upload + release) or `sonaUpload` (upload only, release manually in the portal UI).
 *  - credentials are read from the SONATYPE_USERNAME / SONATYPE_PASSWORD environment variables, which must
 *    hold a Central Portal *user token* (not the legacy OSSRH login).
 */
object PublishSettings extends AutoPlugin {
  override def trigger = allRequirements
  override def requires = plugins.JvmPlugin && ProjectSettings

  private val GithubOwner = "alstanchev"
  private val GithubRepo = "pekko-persistence-inmemory"

  override def projectSettings = Seq(
    organization := "io.github.alstanchev", // This sets the groupId
    name := "pekko-persistence-inmemory", // This sets the artifactId base
    homepage := Some(url(s"https://github.com/$GithubOwner/$GithubRepo")),
    licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
    scmInfo := Some(
      ScmInfo(
        url(s"https://github.com/$GithubOwner/$GithubRepo"),
        s"scm:git:https://github.com/$GithubOwner/$GithubRepo.git",
        Some(s"scm:git:git@github.com:$GithubOwner/$GithubRepo.git")
      )
    ),
    developers := List(
      Developer(
        "alstanchev",
        "Aleksandar Stanchev",
        "aleksandar.stanchev@bosch.com",
        url(s"https://github.com/$GithubOwner/$GithubRepo")
      )
    ),
    publishMavenStyle := true,
    pomIncludeRepository := { _ => false },
    // Publishing destination
    publishTo := {
      val centralSnapshots = "https://central.sonatype.com/repository/maven-snapshots/"
      if (isSnapshot.value) Some("central-snapshots" at centralSnapshots)
      else localStaging.value
    }
  )

}

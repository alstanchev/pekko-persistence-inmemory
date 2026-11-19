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

// to format scala source code
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.3")

// enable updating file headers eg. for copyright
addSbtPlugin("com.github.sbt" % "sbt-header" % "5.11.0")

// sign artifacts for Maven Central.
// Publishing itself uses sbt's built-in Sonatype Central Portal support (sbt >= 1.11: localStaging, sonaRelease)
addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.3.1")
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
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "4.0.0")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.11.0")

//addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.6")

// release and deploy to sonatype
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.12")
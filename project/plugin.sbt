resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"   % "2.4.2")
addSbtPlugin("ch.epfl.scala"     % "sbt-scalafix"   % "0.9.27")
addSbtPlugin("com.geirsson"      % "sbt-ci-release" % "1.5.6")
addSbtPlugin("com.github.duhemm" % "sbt-jni"        % "1.4.3+2-55a5259e-SNAPSHOT")

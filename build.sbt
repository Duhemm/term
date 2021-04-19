import scala.sys.process._
import scala.util.control.NonFatal

val scala211 = "2.11.12"
val scala212 = "2.12.13"
val scala213 = "2.13.5"

inThisBuild(
  List(
    organization := "com.github.duhemm",
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    homepage := Some(url("https://github.com/Duhemm/term")),
    developers := List(
      Developer(
        "@Duhemm",
        "Martin Duhem",
        "martin.duhem@gmail.com",
        url("https://github.com/Duhemm")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/Duhemm/term"),
        "scm:git:git@github.com:Duhemm/term.git"
      )
    ),
    scalaVersion := scala212,
    scalafixCaching := true,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0",
    resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )
)

lazy val `term-size` = project
  .in(file("term-size"))
  .enablePlugins(JniNative)
  .settings(
    javah / target := (nativeCompile / sourceDirectory).value / "include",
    // This is set to false by sbt-jni, because it assumes the projects to contain only native
    // code.
    crossPaths := true,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test",
    crossScalaVersions := List(scala211, scala212, scala213),
    semanticdbEnabled := true,
    scalacOptions += {
      if (scalaBinaryVersion.value == "2.13") "-Wunused"
      else "-Ywarn-unused-import"
    },
    Test / resourceGenerators += Def.task {
      try {
        val cols =
          Process("sh" :: "-c" :: "tput cols 2>/dev/tty" :: Nil).!!.trim
        val rows =
          Process("sh" :: "-c" :: "tput lines 2>/dev/tty" :: Nil).!!.trim
        val out = (Test / resourceManaged).value / "size"
        IO.write(out, s"$cols $rows")
        out :: Nil
      } catch {
        case NonFatal(_) => Nil
      }
    }
  )

publish / skip := true
crossScalaVersions := Nil
Global / semanticdbVersion := scalafixSemanticdb.revision

addCommandAlias("fixCheck", "; scalafix --check ; test:scalafix --check")

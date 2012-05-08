import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "buildmonitor2"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
     "org.scala-tools" %% "scala-stm" % "0.3",
      "com.google.code.gson" % "gson" % "1.4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(

    )

}

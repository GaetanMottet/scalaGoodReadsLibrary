ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

val sparkVersion = "3.2.0"

libraryDependencies ++= Seq(
  ("io.github.vincenzobaz" %% "spark-scala3" % "0.1.3"),
  ("org.apache.spark" %% "spark-core" % sparkVersion).cross(CrossVersion.for3Use2_13),
  ("org.apache.spark" %% "spark-sql" % sparkVersion).cross(CrossVersion.for3Use2_13)
)

lazy val root = (project in file("."))
  .settings(
    name := "scalaGoodReadsLibrary"
  )


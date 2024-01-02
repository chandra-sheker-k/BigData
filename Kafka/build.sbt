ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "kafka",
    idePackagePrefix := Some("com")
  )

libraryDependencies += "it.bitbl" %% "scala-faker" % "0.4"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.4.1"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.4.1" % "provided"
// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.4.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.4.1" % "provided"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
//libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.4.1" % "provided"
// https://mvnrepository.com/artifact/org.apache.spark/spark-graphx
//libraryDependencies += "org.apache.spark" %% "spark-graphx" % "3.4.1"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql-kafka-0-10
//libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.4.1" % Test
// https://mvnrepository.com/artifact/org.apache.spark/spark-avro
//libraryDependencies += "org.apache.spark" %% "spark-avro" % "3.4.1"

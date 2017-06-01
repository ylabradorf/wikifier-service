name := "WikifierService"

version := "1.0"
scalaVersion := "2.11.5"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

//val http4sVersion = "0.17.0-M3"
val http4sVersion = "0.15.0a"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-argonaut" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.6.1"
//  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
//  "org.http4s" %% "http4s-core" % http4sVersion,
 // "org.http4s" %% "http4s-server" % http4sVersion,
  //"org.http4s" %% "http4s-dsl" % http4sVersion,
 // "io.argonaut" %% "argonaut" % "6.0.4",
 // "org.scalaz.stream" %% "scalaz-stream" % "0.6a"
)
ThisBuild / scalaVersion := "3.6.4"

inThisBuild(
  List(
    organization := "io.github.grrrrpc",
    homepage := Some(url("https://github.com/grrrrpc")),
    licenses := List(
      "GPLv3" -> url("https://www.gnu.org/licenses/gpl-3.0.en.html")
    ),
    developers := List(
      Developer(
        "polentino",
        "Diego Casella",
        "polentino911@gmail.com",
        url("https://linkedin.com/in/diegocasella")
      )
    )
  )
)

lazy val zioVersion = "2.1.17"
lazy val squareupWireSchemaVersion = "5.3.1"

lazy val testDependencies = Seq(
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
  "dev.zio" %% "zio-test-magnolia" % zioVersion % Test
)

lazy val lib = (project in file("./lib"))
  .settings(
    name := "grrrRPC lib",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "com.squareup.wire" % "wire-schema-jvm" % squareupWireSchemaVersion
    ) ++ testDependencies
  )

lazy val cli = (project in file("./cli"))
  .enablePlugins(NativeImagePlugin, GraalVMNativeImagePlugin)
  .dependsOn(lib)
  .settings(
    name := "grrrRPC cli",
    libraryDependencies ++= testDependencies,
    Compile / run / mainClass := Some("io.github.polentino.grrr.cli.Main"),
    Compile / mainClass := Some("io.github.polentino.grrr.cli.Main"),
    GraalVMNativeImage / mainClass := Some("io.github.polentino.grrr.cli.Main"),
    GraalVMNativeImage / graalVMNativeImageOptions ++= Seq(
      "-H:+StaticExecutableWithDynamicLibC",
      "--gc=G1",
      "-march=native",
      // todo: perhaps submit a PR to Wire to include that config file :)
      s"-H:ResourceConfigurationFiles=${(Compile / resourceDirectory).value / "META-INF" / "native-image" / "resource-config.json"}"
    ),
    nativeImageReady := { () => println(">>> Native Image Ready! <<<") },
    nativeImageJvm := "graalvm-java24",
    nativeImageVersion := "24.0.1"
  )

lazy val app = (project in file("./app"))
  .settings(
    name := "grrrRPC app",
    libraryDependencies ++= testDependencies
  )

lazy val root = (project in file("."))
  .aggregate(lib, cli, app)
  .settings(
    name := "grrrRPC Suite",
    publish := false
  )

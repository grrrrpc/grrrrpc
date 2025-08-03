package io.github.polentino.grrr.cli

import io.github.polentino.grrr.lib.parsers.WireParser
import zio.*

object Main extends ZIOAppDefault {

  override def run = for {
    path <- ZIO.serviceWith[ZIOAppArgs](_.getArgs.toList.head)
    _ <- ZIO.attempt {
      println(s"PARSING $path")
    }
    services <- ZIO.attempt {
      WireParser(path).doIt()
    }
    _ <- ZIO.attempt {
      println(s"$services")
    }
  } yield ()
}

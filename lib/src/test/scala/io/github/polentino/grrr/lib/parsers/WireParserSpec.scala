package io.github.polentino.grrr.lib.parsers

import io.github.polentino.grrr.lib.parsers.WireParser
import zio.Scope
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertCompletes}

object WireParserSpec extends ZIOSpecDefault {

  override def spec = suite("WireSpec")(
    test("should work") {
      val services = WireParser("lib/src/test/proto-3/simple-request-response").doIt()
      println(s"$services")
      assertCompletes
    }
  )
}

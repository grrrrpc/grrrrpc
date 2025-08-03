package io.github.polentino.grrr.lib.parsers.models

sealed trait GrpcType

object  GrpcType {
  final case class GrpcString(name: String) extends GrpcType
}
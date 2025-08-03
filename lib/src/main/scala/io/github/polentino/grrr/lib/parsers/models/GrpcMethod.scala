package io.github.polentino.grrr.lib.parsers.models

final case class GrpcMethod(name: String, request: GrpcType, response: GrpcType)

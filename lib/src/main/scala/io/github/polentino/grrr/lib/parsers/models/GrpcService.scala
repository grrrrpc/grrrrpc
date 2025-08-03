package io.github.polentino.grrr.lib.parsers.models

final case class GrpcService(name: String, grpcType: GrpcType, methods: List[GrpcMethod])

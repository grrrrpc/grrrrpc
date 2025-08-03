package io.github.polentino.grrr.lib.parsers

// supports FileSystem/Archive (zip,jar, etc..) / .PROTO
import com.squareup.wire.schema.*
import io.github.polentino.grrr.lib.parsers.models.GrpcType.GrpcString
import io.github.polentino.grrr.lib.parsers.models.{GrpcMessage, GrpcMethod, GrpcService}
import okio.FileSystem

import java.io.File
import scala.jdk.CollectionConverters.*


class WireParser(path: String) {

  def doIt(): List[GrpcService] = {
    val location = Location.get(new File(path).getAbsoluteFile.toString)
    val loader = new SchemaLoader(FileSystem.SYSTEM)

    loader.initRoots(List(location).asJava, List.empty[Location].asJava)
    loader.loadSchema().getProtoFiles.asScala.toList.flatMap { protoFile =>
      protoFile.getServices.asScala.toList.map { (service:Service) =>
        val methods = service.rpcs().asScala.toList.map { rpc =>
          GrpcMethod(
            name = rpc.getName,
            request = GrpcString(rpc.getRequestType.getSimpleName),
            response = GrpcString(rpc.getResponseType.getSimpleName)
          )
        }

        GrpcService(
          name = service.name(),
          grpcType = GrpcString(service.name()),
          methods = methods
        )
      }
    }
  }
}

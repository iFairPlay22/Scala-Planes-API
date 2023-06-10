package api.planes.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.alpakka.cassandra.scaladsl.CassandraSession
import api.planes.service.PlaneService
import commons.system.http._HttpControllerSystem
import io.circe.generic.auto._

class PlaneController(implicit val system: ActorSystem, implicit val cassandraSession: CassandraSession)
    extends _HttpControllerSystem {

  private val planeService = new PlaneService()

  override val routes: Route =
    path("planes") {
      get {
        response { () =>
          planeService
            .getAllPlanes()
        }
      }
    }
}

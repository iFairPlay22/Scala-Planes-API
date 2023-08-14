package api.planes.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.alpakka.slick.scaladsl.SlickSession
import api.planes.service.PlaneService
import http._HttpControllerSystem
import io.circe.generic.auto._

class PlaneController(implicit val system: ActorSystem, implicit val dbSession: SlickSession)
    extends _HttpControllerSystem {

  private val planeService = new PlaneService()

  override val routes: Route =
    path("api" / "planes") {
      get {
        complete {
          planeService.getAllPlanes
        }
      }
    }
}

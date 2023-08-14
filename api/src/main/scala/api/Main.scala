package api

import akka.http.scaladsl.server.Route
import api.planes.controller.PlaneController
import commons.actor._ActorSystem
import db._DbSystem
import http._HttpServerSystem

object Main extends _ActorSystem with _DbSystem with _HttpServerSystem {

  override val routes: Route = Route.seal(new PlaneController().routes)

  def main(args: Array[String]): Unit = {
    startServer()
  }

}

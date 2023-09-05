package api

import akka.http.scaladsl.server.Route
import api.planes.controller.PlaneController
import commons.actor._ActorSystem
import db._DbSystem
import http._HttpServerSystem
import http.health._HealthCheckSystem

object Main extends _ActorSystem with _HttpServerSystem with _DbSystem {

  private val health: _HealthCheckSystem = new _HealthCheckSystem()

  override val routes: Route = Route.seal(new PlaneController().routes)

  def main(args: Array[String]): Unit = {
    health.startHealthCheck()
      .flatMap(_ => startServer())
  }

}

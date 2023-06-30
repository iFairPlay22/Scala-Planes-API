package api

import akka.http.scaladsl.server.Route
import api.planes.controller.PlaneController
import cassandra._CassandraSystem
import commons.actor._ActorSystem
import http._HttpServerSystem

object Main extends _ActorSystem with _CassandraSystem with _HttpServerSystem {

  override val routes: Route = Route.seal(new PlaneController().routes)

  def main(args: Array[String]): Unit = {
    startServer()
  }

}

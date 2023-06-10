package api.planes.service

import akka.actor.ActorSystem
import akka.http.scaladsl.model.DateTime
import akka.stream.alpakka.cassandra.scaladsl.CassandraSession
import api.planes.dto.GetPlanesDTO
import api.planes.mapper.PlaneResponseMapper
import commons.system.http._HttpServiceSystem
import database.planes.repositories.PlaneRepository

import java.time.LocalDate
import scala.concurrent.Future

class PlaneService(implicit val system: ActorSystem, implicit val casandraSession: CassandraSession)
    extends _HttpServiceSystem {

  private val planeRepository = new PlaneRepository()

  def getAllPlanes(): Future[GetPlanesDTO] =
    planeRepository
      .selectAllByDate(LocalDate.now)
      .map(PlaneResponseMapper.map)

}

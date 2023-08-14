package api.planes.service

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.SlickSession
import api.planes.dto.GetPlanesDTO
import api.planes.mapper.PlaneResponseMapper
import db.planes.PlaneRepository
import http._HttpServiceSystem

import java.time.LocalDate
import scala.concurrent.Future

class PlaneService(implicit val system: ActorSystem, implicit val dbSession: SlickSession)
    extends _HttpServiceSystem {

  private val planeRepository = new PlaneRepository()

  def getAllPlanes: Future[GetPlanesDTO] =
    planeRepository.getAll
      .map(PlaneResponseMapper.map)

}

package api_test

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import api.planes.controller.PlaneController
import api.planes.dto.GetPlanesDTO
import api.planes.mapper.PlaneResponseMapper
import cassandra._CassandraTestSystem
import database.planes.repositories.PlaneRepository
import domain.planes.PlaneDomain
import org.scalatest.matchers.should.Matchers
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.ExecutionContextExecutor

class ApiSpecs
    extends AnyWordSpecLike
    with ScalatestRouteTest
    with _CassandraTestSystem
    with Matchers
    with ScalaFutures
    with SpecsData {

  override implicit lazy val executor: ExecutionContextExecutor = system.dispatcher

  private val planeRepository: PlaneRepository = new PlaneRepository()
  private val planesRoutes: Route = Route.seal(new PlaneController().routes)

  def testPlanesList(expectedPlanes: PlaneDomain*): Unit =
    Get(f"/api/planes") ~> planesRoutes ~> check {

      status.shouldEqual(StatusCodes.OK)

      val maybeResponse: GetPlanesDTO = decodeResponse[GetPlanesDTO](responseAs[String])
      maybeResponse.planes.shouldEqual(expectedPlanes.map(PlaneResponseMapper.map))
    }

  f"Planes API" should {

    f"return empty planes list" in {

      testPlanesList()

    }

    f"return planes list with 1 element" in {

      await(planeRepository.insertOrEdit(plane = plane1))
      testPlanesList(plane1)

    }

    f"return planes list with 2 elements" in {

      await(planeRepository.insertOrEdit(plane = plane1))
      await(planeRepository.insertOrEdit(plane = plane2))
      testPlanesList(plane1, plane2)

    }

    f"return planes list with 3 elements" in {

      await(planeRepository.insertOrEdit(plane = plane1))
      await(planeRepository.insertOrEdit(plane = plane3))
      await(planeRepository.insertOrEdit(plane = plane2))
      testPlanesList(plane1, plane2, plane3)

    }

    f"return planes list with 2 elements (no duplicates)" in {

      await(planeRepository.insertOrEdit(plane = plane1))
      await(planeRepository.insertOrEdit(plane = plane2))
      await(planeRepository.insertOrEdit(plane = plane2))
      testPlanesList(plane1, plane2)

    }

  }
}

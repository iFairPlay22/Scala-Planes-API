package test_db.planes

import commons.actor._ActorSystem
import db._DbTestSystem
import db.planes.{PlaneRepository, PlaneTable}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class DatabaseSpecs extends _ActorSystem with _DbTestSystem with SpecsData {

  private val planeRepository: PlaneRepository = new PlaneRepository()

  f"return all planes / empty planes list" in {

    val planes = await(planeRepository.getAll)

    planes shouldBe Seq()

  }

  f"return all planes / planes list of length 1" in {

    await(planeRepository.insertOrEdit(plane = plane1))

    val planes = await(planeRepository.getAll)
    planes shouldBe Seq(plane1)

  }

  f"return all planes / planes list of length 2" in {

    await(planeRepository.insertOrEdit(plane = plane1))
    await(planeRepository.insertOrEdit(plane = plane2))

    val planes = await(planeRepository.getAll)
    planes shouldBe Seq(plane1, plane2)

  }

  f"return planes by id / planes of id 1" in {

    await(planeRepository.insertOrEdit(plane = plane1))
    await(planeRepository.insertOrEdit(plane = plane2))

    val planes = await(planeRepository.getById(plane1.id))
    planes shouldBe plane1

  }

  f"return planes by id / planes of id 2" in {

    await(planeRepository.insertOrEdit(plane = plane1))
    await(planeRepository.insertOrEdit(plane = plane2))

    val planes = await(planeRepository.getById(plane2.id))
    planes shouldBe plane2

  }

  override def reset(): Future[Any] =
    db.run(
      DBIO.sequence(
        Seq(
          PlaneTable.PlaneData.schema.dropIfExists,
          PlaneTable.PlaneData.schema.createIfNotExists)))

  override def clean(): Future[Any] =
    db.run(PlaneTable.PlaneData.schema.dropIfExists)
}

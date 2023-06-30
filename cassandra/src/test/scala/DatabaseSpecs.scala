import cassandra._CassandraTestSystem
import commons.actor._ActorSystem
import database.planes.repositories.PlaneRepository
import org.scalatest.matchers.should.Matchers
import org.scalatest.concurrent.ScalaFutures

import java.time.LocalDate

class DatabaseSpecs
    extends _ActorSystem
    with _CassandraTestSystem
    with Matchers
    with ScalaFutures
    with SpecsData {

  private val planeRepository: PlaneRepository = new PlaneRepository()
  private val today: LocalDate = LocalDate.now

  f"return empty planes list" in {

    val planes = await(planeRepository.selectAllByDate(today))

    planes shouldBe Seq()

  }

  f"return planes list of length 1" in {

    await(planeRepository.insertOrEdit(plane = plane1))

    val planes = await(planeRepository.selectAllByDate(today))
    planes shouldBe Seq(plane1)

  }

  f"return planes list of length 2" in {

    await(planeRepository.insertOrEdit(plane = plane1))
    await(planeRepository.insertOrEdit(plane = plane2))

    val planes = await(planeRepository.selectAllByDate(today))
    planes shouldBe Seq(plane1, plane2)

  }

  f"return planes list of length 3" in {

    await(planeRepository.insertOrEdit(plane = plane1))
    await(planeRepository.insertOrEdit(plane = plane3))
    await(planeRepository.insertOrEdit(plane = plane2))

    val planes = await(planeRepository.selectAllByDate(today))
    planes shouldBe Seq(plane1, plane2, plane3)

  }

  f"return planes list of length (no duplicates)" in {

    await(planeRepository.insertOrEdit(plane = plane1))
    await(planeRepository.insertOrEdit(plane = plane2))
    await(planeRepository.insertOrEdit(plane = plane2))

    val planes = await(planeRepository.selectAllByDate(today))
    planes shouldBe Seq(plane1, plane2)

  }
}

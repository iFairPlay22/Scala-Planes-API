package db.planes

import akka.Done
import akka.actor.ActorSystem
import db._DbSystem
import slick.jdbc.H2Profile.api._
import domain.planes.PlaneDomain

import scala.concurrent.Future

object PlaneTable {
  val PlaneData = TableQuery[PlaneRow]
}

class PlaneRow(tag: Tag) extends Table[PlaneDomain](tag, Some("db_ewen"), "planes") {

  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name")
  def * = (id, name) <> (PlaneDomain.tupled, PlaneDomain.unapply)

}

class PlaneRepository(implicit val system: ActorSystem) extends _DbSystem {

  def insertOrEdit(plane: PlaneDomain): Future[Done] =
    db.run(PlaneTable.PlaneData += plane)
      .map(_ => Done)

  def getAll: Future[Seq[PlaneDomain]] =
    db.run(PlaneTable.PlaneData.result)

  def getById(id: Int): Future[PlaneDomain] =
    db.run(PlaneTable.PlaneData.filter(_.id === id).result.head)

}

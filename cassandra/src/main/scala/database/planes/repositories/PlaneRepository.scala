package database.planes.repositories

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.cassandra.scaladsl.CassandraSession
import cassandra._CassandraRepositorySystem
import com.datastax.oss.driver.api.core.cql.Row
import database.planes.keyspace.Keyspace._
import domain.planes.PlaneDomain

import java.time.LocalDate
import scala.concurrent.Future

class PlaneRepository(
    implicit val system: ActorSystem,
    implicit val cassandraSession: CassandraSession)
    extends _CassandraRepositorySystem {

  import PlaneRepository._

  def insertOrEdit(plane: PlaneDomain): Future[Done] =
    queryToEmptyResult(insertOrEditQuery, List(LocalDate.now(), plane.id, plane.name))

  def selectAllByDate(date: LocalDate): Future[Seq[PlaneDomain]] = {
    queryToMultipleResult(selectAllByDateQuery, List(date), mapRowToEntity)
  }

  private def mapRowToEntity(row: Row): PlaneDomain =
    PlaneDomain(row.getInt(table_plane_field_id), row.getString(table_plane_field_name))
}

object PlaneRepository {

  private val insertOrEditQuery: String =
    f"""
       > INSERT INTO $table_plane_name
       > ($table_plane_field_date_bucket, $table_plane_field_id, $table_plane_field_name)
       > VALUES (?, ?, ?);
       > """.stripMargin('>')

  private val selectAllByDateQuery: String =
    f"""
       > SELECT * FROM $table_plane_name WHERE $table_plane_field_date_bucket = ?;
       > """.stripMargin('>')
}

package broker_consumer.planes.consumers

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.model.DateTime
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import com.typesafe.scalalogging.Logger
import commons.system.broker._BrokerConsumerSystem
import commons.system.database._CassandraSystem
import database.planes.repositories.PlaneRepository
import domain.planes.PlaneDomain

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class PlaneBrokerConsumer(implicit val system: ActorSystem)
    extends _BrokerConsumerSystem[DateTime, PlaneDomain]
    with _CassandraSystem {

  private val logger: Logger = Logger(getClass)

  private val planeRepository = new PlaneRepository()

  override val callbacks: Set[(DateTime, PlaneDomain) => Future[Done]] = Set((_, plane) => {
    logger.info("Processing a consumed plane")
    planeRepository
      .insertOrEdit(plane)
      .andThen(_ => logger.info("Plane inserted in cassandra!"))
  })

  override def startBrokerConsumer(): Future[Done] = {
    super.startBrokerConsumer()
  }

  override def stopBrokerConsumer(): Future[Done] =
    super
      .stopBrokerConsumer()
      .andThen(_ => stopCassandra())
}

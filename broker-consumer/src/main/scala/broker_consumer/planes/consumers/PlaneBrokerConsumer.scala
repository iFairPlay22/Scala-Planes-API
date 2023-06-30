package broker_consumer.planes.consumers

import akka.Done
import akka.actor.ActorSystem
import broker._BrokerConsumerSystem
import cassandra._CassandraSystem
import io.circe.generic.auto._
import com.typesafe.scalalogging.Logger
import database.planes.repositories.PlaneRepository
import domain.planes.PlaneDomain

import java.time.LocalDateTime
import scala.concurrent.Future

class PlaneBrokerConsumer(implicit val system: ActorSystem)
    extends _BrokerConsumerSystem[LocalDateTime, PlaneDomain]
    with _CassandraSystem {

  private val logger: Logger = Logger(getClass)

  private val planeRepository = new PlaneRepository()

  override val callbacks: Set[(LocalDateTime, PlaneDomain) => Future[Done]] = Set((_, plane) => {
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

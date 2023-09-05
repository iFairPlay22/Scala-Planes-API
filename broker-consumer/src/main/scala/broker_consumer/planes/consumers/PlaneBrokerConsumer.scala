package broker_consumer.planes.consumers

import akka.Done
import akka.actor.ActorSystem
import broker._BrokerConsumerSystem
import io.circe.generic.auto._
import com.typesafe.scalalogging.Logger
import db._DbSystem
import db.planes.PlaneRepository
import domain.planes.PlaneDomain
import org.slf4j.LoggerFactory

import java.time.LocalDateTime
import scala.concurrent.Future

class PlaneBrokerConsumer(implicit val system: ActorSystem)
    extends _BrokerConsumerSystem[LocalDateTime, PlaneDomain]
      with _DbSystem {

  private val logger = LoggerFactory.getLogger(getClass)

  private val planeRepository = new PlaneRepository()

  override val callbacks: Set[(LocalDateTime, PlaneDomain) => Future[Done]] = Set((_, plane) => {
    logger.info("Processing a consumed plane")
    planeRepository
      .insertOrEdit(plane)
      .andThen(_ => logger.info("Plane inserted in DB!"))
  })

  override def startBrokerConsumer(): Future[Done] = {
    super.startBrokerConsumer()
  }

  override def stopBrokerConsumer(): Future[Done] =
    super
      .stopBrokerConsumer()
      .andThen(_ => stopDb())
}

package broker_producer.planes.schedulers

import akka.Done
import akka.actor.{ActorSystem, Cancellable}
import broker_producer.planes.producers.PlaneBrokerProducer
import com.typesafe.scalalogging.Logger
import commons.system.actor._ActorSystem
import commons.system.scheduler._SchedulerSystem
import domain.planes.PlaneDomain

import java.time.Duration
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class PlaneScheduler(implicit val system: ActorSystem) extends _SchedulerSystem {

  private val logger: Logger = Logger(getClass)

  override val initialDelay: Duration =
    config.getDuration("broker_producer.scheduler.initial-delay");
  override val refreshDelay: Duration =
    config.getDuration("broker_producer.scheduler.refresh-delay");

  private final val brokerProducer: PlaneBrokerProducer = new PlaneBrokerProducer()
  override def startScheduler(): Future[Done] =
    brokerProducer
      .startBrokerProducer()
      .andThen(_ => super.startScheduler())

  override def stopScheduler(): Future[Done] =
    super
      .stopScheduler()
      .andThen(_ => brokerProducer.stopBrokerProducer())

  override val action: Unit => Future[Done] = _ => {
    logger.info("Producing plane in topic")
    brokerProducer
      .produce(PlaneDomain.randomValid())
      .andThen(_ => logger.info("Successfully produced plane in topic!"))
      .map(_ => Done.done())
  }

}

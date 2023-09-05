package broker_producer.planes.schedulers

import akka.Done
import akka.actor.ActorSystem
import broker_producer.planes.producers.PlaneBrokerProducer
import com.typesafe.scalalogging.Logger
import domain.planes.{PlaneDomain, PlaneDomainRnd}
import org.slf4j.LoggerFactory
import scheduler._SchedulerSystem

import scala.concurrent.Future

class PlaneScheduler(val brokerProducer: PlaneBrokerProducer)(implicit val system: ActorSystem) extends _SchedulerSystem {

  private val logger = LoggerFactory.getLogger(getClass)

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
      .produce(PlaneDomainRnd.randomValid())
      .andThen(_ => logger.info("Successfully produced plane in topic!"))
      .map(_ => Done.done())
  }

}

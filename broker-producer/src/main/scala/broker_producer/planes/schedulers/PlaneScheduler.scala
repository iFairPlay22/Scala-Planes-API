package broker_producer.planes.schedulers

import akka.Done
import akka.actor.ActorSystem
import broker_producer.planes.producers.PlaneBrokerProducer
import com.typesafe.scalalogging.Logger
import domain.planes.PlaneDomain
import scheduler._SchedulerSystem

import scala.concurrent.Future

class PlaneScheduler(implicit val system: ActorSystem) extends _SchedulerSystem {

  private val logger: Logger = Logger(getClass)

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

package broker_producer

import broker_producer.planes.producers.PlaneBrokerProducer
import broker_producer.planes.schedulers.PlaneScheduler
import commons.actor._ActorSystem
import http.health._HealthCheckSystem

object Main extends _ActorSystem {

  private val health: _HealthCheckSystem = new _HealthCheckSystem(() => !scheduler.isSchedulerStopped || !brokerProducer.isBrokerProducerStopped)

  final val brokerProducer: PlaneBrokerProducer = new PlaneBrokerProducer()

  final val scheduler: PlaneScheduler = new PlaneScheduler(brokerProducer)

  def main(args: Array[String]): Unit =
    health.startHealthCheck()
      .flatMap(_ => scheduler.startScheduler())
}

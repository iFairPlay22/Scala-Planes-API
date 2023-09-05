package broker_consumer
import broker_consumer.planes.consumers.PlaneBrokerConsumer
import commons.actor._ActorSystem
import http.health._HealthCheckSystem

object Main extends _ActorSystem {

  private val health: _HealthCheckSystem = new _HealthCheckSystem(() => !consumer.isBrokerConsumerStopped || !consumer.isDbStopped)

  final val consumer: PlaneBrokerConsumer = new PlaneBrokerConsumer()

  def main(args: Array[String]): Unit =
    health.startHealthCheck()
      .flatMap(_ => consumer.startBrokerConsumer())
}

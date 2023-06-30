package broker_consumer
import broker_consumer.planes.consumers.PlaneBrokerConsumer
import commons.actor._ActorSystem

object Main extends _ActorSystem {

  final val consumer: PlaneBrokerConsumer = new PlaneBrokerConsumer()

  def main(args: Array[String]): Unit =
    consumer.startBrokerConsumer()

}

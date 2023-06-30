package broker_producer

import broker_producer.planes.schedulers.PlaneScheduler
import commons.actor._ActorSystem

object Main extends _ActorSystem {

  final val scheduler: PlaneScheduler = new PlaneScheduler()

  def main(args: Array[String]): Unit =
    scheduler.startScheduler()

}

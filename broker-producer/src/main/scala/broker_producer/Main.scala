package broker_producer

import akka.actor.ActorSystem
import broker_producer.planes.schedulers.PlaneScheduler
import commons.system.actor._ActorSystem

object Main extends _ActorSystem {

  final val scheduler: PlaneScheduler = new PlaneScheduler()

  def main(args: Array[String]): Unit =
    scheduler.startScheduler()

}

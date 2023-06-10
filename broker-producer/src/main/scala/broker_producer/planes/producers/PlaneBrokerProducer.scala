package broker_producer.planes.producers

import io.circe.generic.auto._
import akka.actor.ActorSystem
import akka.http.scaladsl.model.DateTime
import commons.system.broker._BrokerProducerSystem
import domain.planes.PlaneDomain
import org.apache.kafka.clients.producer.RecordMetadata

import scala.concurrent.Future
import scala.util.Try

class PlaneBrokerProducer(implicit val system: ActorSystem)
    extends _BrokerProducerSystem[DateTime, PlaneDomain] {

  def produce(plane: PlaneDomain): Future[RecordMetadata] =
    super.produce(DateTime.now, plane)

}

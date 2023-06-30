package broker_producer.planes.producers

import io.circe.generic.auto._
import akka.actor.ActorSystem
import broker._BrokerProducerSystem
import domain.planes.PlaneDomain
import org.apache.kafka.clients.producer.RecordMetadata

import java.time.LocalDateTime
import scala.concurrent.Future

class PlaneBrokerProducer(implicit val system: ActorSystem)
    extends _BrokerProducerSystem[LocalDateTime, PlaneDomain] {

  def produce(plane: PlaneDomain): Future[RecordMetadata] =
    super.produce(LocalDateTime.now, plane)

}

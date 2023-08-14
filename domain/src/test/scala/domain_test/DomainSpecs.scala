package domain_test

import domain.planes.{PlaneDomain, PlaneDomainRnd}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DomainSpecs extends AnyWordSpec with Matchers {

  "Planes domain" should {

    Range
      .inclusive(0, 50)
      .foreach(r => {

        s"be invalid $r" in {
          assertThrows[IllegalArgumentException] {
            PlaneDomainRnd.randomInvalid()
          }
        }

        s"be valid $r" in {
          noException shouldBe thrownBy {
            PlaneDomainRnd.randomValid()
          }
        }

      })

  }

}

package test_db.planes

import domain.planes.{PlaneDomain, PlaneDomainRnd}

trait SpecsData {

  // Random valid planes
  val plane1: PlaneDomain = PlaneDomainRnd.randomValid().copy(id = 1)
  val plane2: PlaneDomain = PlaneDomainRnd.randomValid().copy(id = 2)
  val plane3: PlaneDomain = PlaneDomainRnd.randomValid().copy(id = 3)

}

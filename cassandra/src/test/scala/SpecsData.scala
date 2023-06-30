import domain.planes.PlaneDomain

trait SpecsData {

  // Random valid planes
  val plane1: PlaneDomain = PlaneDomain.randomValid().copy(id = 1)
  val plane2: PlaneDomain = PlaneDomain.randomValid().copy(id = 2)
  val plane3: PlaneDomain = PlaneDomain.randomValid().copy(id = 3)

}

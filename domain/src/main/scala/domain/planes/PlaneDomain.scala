package domain.planes

import commons.random._RandomGen

case class PlaneDomain(id: Int, name: String) {
  require(0 <= id, "plane id must be positive or zero")
  require(name.nonEmpty, "plane name must be non empty")
  require(
    3 <= name.length() && name.length() <= 10,
    "plane name must have a length between 3 and 10")
}

object PlaneDomainRnd extends _RandomGen[PlaneDomain] {
  override def randomValid(): PlaneDomain =
    PlaneDomain(id = randomInt(), name = randomString(randomInt(3, 10)))

  override def randomInvalid(): PlaneDomain =
    randomInt(0, 2) match {
      case 0 =>
        randomValid()
          .copy(id = randomInt(start = -100, end = -1))
      case 1 =>
        randomValid()
          .copy(name = randomString(randomInt(0, 2)))
      case 2 =>
        randomValid()
          .copy(name = randomString(randomInt(11, 100)))
      case _ => throw new NotImplementedError()
    }
}

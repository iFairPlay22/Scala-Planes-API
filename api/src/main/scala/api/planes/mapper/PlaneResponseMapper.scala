package api.planes.mapper

import api.planes.dto.{GetPlanesDTO, PlaneDTO}
import domain.planes.PlaneDomain

object PlaneResponseMapper {

  def map(plane: PlaneDomain): PlaneDTO =
    PlaneDTO(plane.id, plane.name)

  def map(planes: Seq[PlaneDomain]): GetPlanesDTO =
    GetPlanesDTO(
      planes
        .map(map))
}

package api.planes.dto

import http._DTO

case class GetPlanesDTO(planes: Seq[PlaneDTO]) extends _DTO

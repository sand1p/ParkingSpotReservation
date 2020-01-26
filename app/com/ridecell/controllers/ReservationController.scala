package com.ridecell.controllers

import com.ridecell.models.ReservationDTO
import com.ridecell.services.ReservationService
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Format, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * CREATE parkingsystem.reservation(
  * userId int,
  * spotId int,
  * reservationId int,
  * startTime Date,
  * endTime Date
  * PRIMARY KEY (spotId, reservationId)
  * )
  *
  * To write APIs I must understand few database terms such as
  * 1. Primary key designing
  * 2. How to have unique key for primary key
  * 3.
  *
  * @param cc
  */
@Singleton
class ReservationController @Inject()(cc: ControllerComponents, reservationService: ReservationService) extends AbstractController(cc) {

  implicit val secretFormat: Format[ReservationDTO] = Json.format[ReservationDTO]


  /**
    * • Reserve a parking spot
    */

  def reserve = Action(parse.json) { request =>
    val reservationDTO: Option[ReservationDTO] = request.body.asOpt[ReservationDTO]

    reservationDTO match {
      case Some(reservation) => reservationService.reserve(reservation)
        Ok
      case None => BadRequest
    }

  }


}

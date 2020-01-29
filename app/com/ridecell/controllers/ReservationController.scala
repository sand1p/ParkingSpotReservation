package com.ridecell.controllers

import java.util.UUID

import com.ridecell.models._
import com.ridecell.services.ReservationService
import com.ridecell.utils.XMLParser
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, Headers}

import scala.xml.NodeSeq

@Singleton
class ReservationController @Inject()(cc: ControllerComponents, reservationService: ReservationService) extends AbstractController(cc) {

  /**
    * Reserve a parking spot
    *
    * @return reservation Id
    */

  def reserve = Action(parse.xml) { request =>
    xmlToReservationDTO(request.body, request.headers) match {
      case Some(reservationDTO) =>
        reservationService.reserve(reservationDTO).map { id =>
          Ok(<response>
            <status>Success</status>
            <message>
              {id}
            </message>
          </response>)
        }.getOrElse(NotFound(<response>
          <status>Failure</status>
          <message>Parking Spot is not available.</message>
        </response>))

      case None =>
        BadRequest(<response>
          <status>Failure</status>
          <message>Insufficient Data.</message>
        </response>)
    }
  }

  /**
    * This method converts XML request body to Reservation
    *
    * @param requestBody
    * @return
    */
  private def xmlToReservationDTO(requestBody: NodeSeq, headers: Headers): Option[Reservation] = {
    val xmlParser = new XMLParser(requestBody)
    val id = UUID.randomUUID()
    for {
      userId <- headers.get("userId").map(UUID.fromString)
      spotId <- xmlParser.get("spotId").map(UUID.fromString)
      status <- xmlParser.get("status")
      startTime <- xmlParser.get("startTimeEpoch").map(x => x.toLong)
    } yield {
      import ReservationStatusFactory._
      Reservation(id = id, userId = userId, spotId = spotId, getInstance(status), startTime = startTime, endTime = None, totalCharges = None)
    }
  }

  /**
    * View existing reservations based on status {Active, Completed, Reserve}
    *
    * @param status
    * @return
    */
  def getReservations(status: Option[String]) = Action { request =>
    val userId: Option[String] = request.headers.get("user_id")
    val reservations = for {
      status <- status
      userId <- userId
    } yield {
      reservationService.getReservations(userId.trim, status)
    }
    reservations match {
      case Some(reservations) =>
        Ok(<response>
          {<status>Success</status>
            <reservations>
              {reservations.map(reservation => reservation.asXML)}
            </reservations>}
        </response>)
      case None =>
        BadRequest(<response>
          <status>Failure</status>
          <message>UserId/ReservationStatus not found in Request</message>
        </response>)
    }
  }

  /**
    * Cancel an existing reservation
    */
  def cancel(reservationId: String, spotId: String) = Action { request =>
    val userId = request.headers.get("user_id")
    val totalCharges = userId.flatMap { uId => reservationService.cancel(reservationId, uId, spotId) }
    totalCharges match {
      case Some(charges) => Ok(<response>
        {<status>Success</status>
          <totalCharges>
            {charges}
          </totalCharges>}
      </response>)
      case None => BadRequest(<response>
        <status>Failure</status>
        <message>UserId/ReservationStatus not found in Request</message>
      </response>)
    }
  }

  /**
    *
    * Show the user the cost of the reservation
    * @param reservationId
    * @param spotId
    * @return
    */
  def getCost(reservationId: String, spotId: String) = Action{ request =>
    val userId = request.headers.get("user_id")
    val totalCharges = userId.flatMap { uId => reservationService.getTotalCharges(reservationId, uId, spotId) }
    totalCharges match {
      case Some(charges) => Ok(<response>
        {<status>Success</status>
          <cost>{charges}</cost>}
      </response>)
      case None => BadRequest(<response>
        <status>Failure</status>
        <message>UserId/ReservationStatus not found in Request</message>
      </response>)
    }
  }


}

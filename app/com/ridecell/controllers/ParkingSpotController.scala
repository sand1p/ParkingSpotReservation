package com.ridecell.controllers

import com.ridecell.models.ParkingSpot
import com.ridecell.services.ParkingSpotService
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.xml.NodeSeq

@Singleton
class ParkingSpotController @Inject()(cc: ControllerComponents,
                                      parkingSpotService: ParkingSpotService) extends AbstractController(cc) {

  /**
    * See available parking spots on a map
    *
    * @return
    */
  def getAvailableParkingSpots = Action { request =>
    val parkingSpots: List[ParkingSpot] = parkingSpotService.getAvailable
    Ok(toParkingSpotXML(parkingSpots))
  }

  /**
    * Search for an address and find nearby parking spot. (input: lat, lng, radius in meters. Output - list of
    * parking spots within the radius).
    */

  def search(lat: Double, lng: Double, radius: Double) = Action { request =>
    val userId = request.headers.get("userId")
    val parkingSpots: List[ParkingSpot] = parkingSpotService.search(lat, lng, radius)
    Ok(toParkingSpotXML(parkingSpots))
  }

  private def toParkingSpotXML(parkingSpots: List[ParkingSpot]): NodeSeq = {
    <response>
      <status>Success</status> <parkingSpots>
      {parkingSpots.map(parkingSpot => parkingSpot.asXML())}
    </parkingSpots>
    </response>
  }

}

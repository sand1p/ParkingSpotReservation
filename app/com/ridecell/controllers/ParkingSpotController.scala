package com.ridecell.controllers

import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import com.ridecell.services.ParkingSpotService

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
    Ok(<parkingSpots>
      <status>Success</status>
      {parkingSpots.map(parkingSpot => parkingSpot.asXML())}
    </parkingSpots>)
  }

}

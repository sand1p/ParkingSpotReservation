package com.ridecell.controllers

import com.ridecell.models.ParkingSpot
import com.ridecell.services.ParkingSpotService
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

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
    Ok(<response>{
      <status>Success</status>
        <parkingSpots>
          {parkingSpots.map(parkingSpot => parkingSpot.asXML())}
        </parkingSpots>}
    </response>)
  }

}

package com.ridecell.controllers

import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import com.ridecell.services.ParkingSpotService

@Singleton
class ParkingSpotController @Inject()(cc: ControllerComponents,
                                      parkingSpotService: ParkingSpotService) extends AbstractController(cc) {

  /**
    * API to return price per hour for parking spot
    *
    * @param spotId
    * @return Price per hour for parking spot
    */
  def getPricePerHour(spotId: Int) = Action { request =>
    val pricePerHour = parkingSpotService.getPricePerHour(spotId)
    Ok(<result>
      <status>Success</status>
      <spotId>
        {spotId}
      </spotId>
      <pricePerHour>
        {pricePerHour}
      </pricePerHour>
    </result>)
  }

  /**
    * API to get list of parking spots based on no criteria
    *
    * @return
    */
  def getParkingSpots() = Action { request =>
    val parkingSpots: List[ParkingSpot] = parkingSpotService.getParkingSpots()
    Ok(<parkingSpots>
      <status>Success</status>
      {parkingSpots.map { parkingSpot => parkingSpot.asXML() }}
    </parkingSpots>)
  }

}

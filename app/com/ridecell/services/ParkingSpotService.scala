package com.ridecell.services

import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}
import com.ridecell.repositories.ParkingSpotRepository

@Singleton
class ParkingSpotService @Inject()(parkingSpotRepository: ParkingSpotRepository) {
  def allocate(parkingSpot: ParkingSpot): Boolean = {
    parkingSpotRepository.allocate(parkingSpot)
  }

  def getAvailable : List[ParkingSpot] = parkingSpotRepository.getAvailable

}

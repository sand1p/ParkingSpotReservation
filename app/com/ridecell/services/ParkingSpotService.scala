package com.ridecell.services

import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}
import com.ridecell.repositories.ParkingSpotRepository

@Singleton
class ParkingSpotService @Inject()(parkingSpotRepository: ParkingSpotRepository) {
  def getParkingSpots(): List[ParkingSpot] = parkingSpotRepository.getParkingSpots()

  def getPricePerHour(spotId: Int): Int = {
    parkingSpotRepository.getPricePerHour(spotId)
  }
}

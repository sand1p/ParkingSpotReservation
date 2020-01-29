package com.ridecell.services

import java.util.UUID

import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}
import com.ridecell.repositories.ParkingSpotRepository

@Singleton
class ParkingSpotService @Inject()(parkingSpotRepository: ParkingSpotRepository) {

  def getChargesPerMinute(spotId: UUID): Option[Int] = {
    parkingSpotRepository.getCostPerHour(spotId)
  }

  def allocate(parkingSpot: ParkingSpot): Unit = {
    parkingSpotRepository.allocate(parkingSpot.id)
  }

  def getAvailable : List[ParkingSpot] = parkingSpotRepository.getAvailable

  def deAllocate(spotId: String): Unit = {
    parkingSpotRepository.deAllocate(spotId)
  }
}

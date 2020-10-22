package com.ridecell.services

import java.util.UUID

import com.ridecell.models.ParkingSpot
import com.ridecell.repositories.ParkingSpotRepository
import javax.inject.{Inject, Singleton}
import play.api.Logger

@Singleton
class ParkingSpotService @Inject()(parkingSpotRepository: ParkingSpotRepository) {

  def getChargesPerMinute(spotId: UUID): Option[Int] = {
    parkingSpotRepository.getCostPerMinute(spotId)
  }

  def search(lat: Double, lng: Double, radius: Double): List[ParkingSpot] = {
    parkingSpotRepository.getAvailable.filter { parkingSpot =>
      val xDiff = Math.pow(lat - parkingSpot.lat, 2)
      val yDiff = Math.pow(lng - parkingSpot.lng, 2)
      val distance = Math.sqrt(xDiff + yDiff)
      Logger.info(s"Search API: euclidean  Distance : $distance")
      distance <= radius
    }
  }

  def allocate(parkingSpot: ParkingSpot): Unit = {
    parkingSpotRepository.allocate(parkingSpot.id)
  }

  def getAvailable: List[ParkingSpot] = parkingSpotRepository.getAvailable

  def deAllocate(spotId: String): Unit = {
    parkingSpotRepository.deAllocate(spotId)
  }
}

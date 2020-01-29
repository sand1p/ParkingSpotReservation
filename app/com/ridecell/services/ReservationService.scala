package com.ridecell.services

import java.util.UUID

import com.ridecell.models.Reservation
import com.ridecell.repositories.ReservationRepository
import javax.inject.{Inject, Singleton}


@Singleton
class ReservationService @Inject()(reservationRepository: ReservationRepository, parkingSpotService: ParkingSpotService) {

  def getReservations(userId: String, status: String): List[Reservation] = {
    reservationRepository.get(userId).filter(reservationStatus => reservationStatus.status.toString == status)
  }

  def reserve(reservationDTO: Reservation): Option[UUID] = {
    import reservationDTO._

    def allocateParkingSpot: Option[Boolean] = {
      parkingSpotService.getAvailable.find(parkingSpot => parkingSpot.id == spotId)
        .map(parkingSpot => parkingSpotService.allocate(parkingSpot))
    }

    val allocated = true
    if (allocateParkingSpot.contains(allocated)) {
      reservationRepository.reserve(Reservation(id = id, userId = userId, spotId = spotId, status = status, startTime = startTime, endTime = None, totalCharges = None))
      Some(id)
    } else None
  }

}

package com.ridecell.services

import java.util.UUID

import com.ridecell.models.{Reservation, ReservationStatusFactory}
import com.ridecell.repositories.ReservationRepository
import com.ridecell.utils.CalculationUtils
import javax.inject.{Inject, Singleton}

@Singleton
class ReservationService @Inject()(reservationRepository: ReservationRepository, parkingSpotService: ParkingSpotService, calculationUtils: CalculationUtils) {

  def cancel(id: String, userId: String, spotId: String): Option[Double] = {
    reservationRepository.updateStatus(id, userId, ReservationStatusFactory.getInstance("Completed"))
    parkingSpotService.deAllocate(spotId)
    getTotalCharges(id, userId, spotId)
  }

  def getTotalCharges(reservationId: String, userId: String, spotId: String): Option[Double] = {
    val total_charges: Option[Double] = for {
      reservation <- reservationRepository.get(userId: String, reservationId: String)
      costPerMinute <- parkingSpotService.getChargesPerMinute(reservation.spotId)
      endTime <- reservation.endTime
    } yield {
      calculationUtils.calculateTotalCharge(costPerMinute, reservation.startTime, endTime)
    }
    total_charges
  }

  def getTotalCharges(spotId: UUID, startTimeEpoch: Long, endTimeEpoch: Long): Option[Double] = {
    parkingSpotService.getChargesPerMinute(spotId).map { costPerMinute =>
      calculationUtils.calculateTotalCharge(costPerMinute, startTimeEpoch, endTimeEpoch)
    }
  }

  def getReservations(userId: String, status: String): List[Reservation] = {
    reservationRepository.get(userId).filter(reservationStatus => reservationStatus.status.toString == status)
  }

  def reserve(reservation: Reservation): Option[UUID] = {
    import reservation._
    def allocateParkingSpot: Option[Unit] = {
      parkingSpotService.getAvailable.find(parkingSpot => parkingSpot.id == spotId)
        .map(parkingSpot => parkingSpotService.allocate(parkingSpot))
    }
    allocateParkingSpot.map{ _ =>
      reservationRepository.reserve(Reservation(id = id, userId = userId, spotId = spotId, status = status, startTime = startTime, endTime = None, totalCharges = None))
      id
    }
  }

}

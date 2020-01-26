package com.ridecell.services

import java.sql.Timestamp
import java.time.{Instant, LocalDateTime}
import java.util.{Date, UUID}

import com.ridecell.models.{Reservation, ReservationDTO}
import com.ridecell.repositories.ReservationRepository
import javax.inject.{Inject, Singleton}

@Singleton
class ReservationService @Inject()(reservationRepository: ReservationRepository, parkingSpotService: ParkingSpotService) {

  def reserve(reservationDTO: ReservationDTO): Unit = {
    val id = UUID.randomUUID
    val charges =0
    val reservation = Reservation(id, id, id, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),charges)
    reservationRepository.create(reservation)
  }

  def toModel(): Unit ={
    val numberOfHours = 2
    val now = Instant.now()
    val entryTime = Timestamp.from(now)
    val exitTime =  Timestamp.from(now.plusSeconds(3600*numberOfHours))
  }
  def getCharges(startTime: LocalDateTime, endTime: LocalDateTime): Double = {
    val result = getDiffernceInHours(startTime, endTime)
    parkingSpotService.getPricePerHour("")
  }

  //  ToDo
  private def getDiffernceInHours(startTime: LocalDateTime, endTime: LocalDateTime): Int = {
    val hours = (endTime.getSecond - startTime.getSecond) / 60
    20
  }


}

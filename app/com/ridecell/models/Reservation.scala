package com.ridecell.models

import java.sql.Timestamp
import java.util.UUID

case class Reservation(id: UUID, userId: UUID, spotId: UUID, entry_time: Timestamp, exit_time: Timestamp, charges: BigDecimal)

case class ReservationDTO(userId: String, spotId: String, numberOfHours: Int)
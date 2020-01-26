package com.ridecell.repositories

import java.sql.{PreparedStatement, Timestamp}

import com.ridecell.connections.{CassandraClient, PostgresClient}
import com.ridecell.models.Reservation
import javax.inject.{Inject, Singleton}

/**
  *
  */
@Singleton
class ReservationRepository @Inject()(postgresClient: PostgresClient) {
  private val connection = postgresClient.getConnection
  private val reservationTable = "reservation"
  /*
  CREATE TABLE reservation
(
    id        uuid PRIMARY KEY NOT NULL,
    user_id   uuid  NOT NULL,
	spot_id   uuid  NOT NULL,
	entry_time timestamp NOT NULL,
    exit_time  timestamp,
    charges   bigint,
	Foreign Key (user_id) references users(id),
    Foreign Key (spot_id) references parking_spots(id)
)
   */

  //  insert into reservation(id, user_id, spot_id, entry_time,exit_time, charges)
  // values ('','user_id', 'spot_id', entry_time,exit_time);
  private val reservationStatement: PreparedStatement = connection.prepareStatement(s"insert into $reservationTable(id, user_id, spot_id, entry_time, exit_time, charges)" +
    "values (?,?,?,?,?,?)")

  def create(reservation: Reservation): Boolean = {
    reservationStatement.setString(1, reservation.id.toString)
    reservationStatement.setString(2, reservation.userId.toString)
    reservationStatement.setString(3, reservation.spotId.toString)
    reservationStatement.setTimestamp(4, reservation.entry_time)
    reservationStatement.setTimestamp(5, reservation.exit_time)
    reservationStatement.setBigDecimal(6, reservation.charges.bigDecimal)
    reservationStatement.execute()
  }

}
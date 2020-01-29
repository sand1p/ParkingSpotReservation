package com.ridecell.repositories

import java.util.{Date, UUID}

import com.datastax.driver.core.{PreparedStatement, Row}
import com.ridecell.connections.CassandraClient
import com.ridecell.models.{Reservation, ReservationStatusFactory}
import javax.inject.{Inject, Singleton}

import scala.collection.JavaConverters._

@Singleton
class ReservationRepository @Inject()(cassandraClient: CassandraClient) {
  private val keyspace = "parking_system"
  private val connection = cassandraClient.getSession(keyspace)

  private val parkingSpot = "reservation"

  private val reserveStatement: PreparedStatement = connection.prepare(s"INSERT INTO $parkingSpot(id, user_id, spot_id, status, start_time) values(?,?,?,?,?)")
  private val getStatement: PreparedStatement = connection.prepare(s"select * from $parkingSpot where user_id = ?")

  def reserve(reservation: Reservation): Boolean = {
    import reservation._
    connection.execute(reserveStatement.bind(id, userId, spotId, status.toString,new Date(startTime*1000))).wasApplied()
  }

  def get(userId: String): List[Reservation] = {
    connection.execute(getStatement.bind(UUID.fromString(userId))).asScala.map(toReservation).toList
  }

  def toReservation(row: Row): Reservation = {
    import row._
    Reservation(
      getUUID("id"),
      getUUID("user_id"),
      getUUID("spot_id"),
      ReservationStatusFactory.get("status"),
      getDate("start_time").getMillisSinceEpoch,
      Some(getDate("end_time").getDaysSinceEpoch),
      Some(getDouble("total_charges"))
    )
  }



}

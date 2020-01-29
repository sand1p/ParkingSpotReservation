package com.ridecell.repositories

import java.util.{Date, UUID}

import com.datastax.driver.core.{PreparedStatement, Row}
import com.ridecell.connections.CassandraClient
import com.ridecell.models.{Reservation, ReservationStatus, ReservationStatusFactory}
import javax.inject.{Inject, Singleton}

import scala.collection.JavaConverters._

@Singleton
class ReservationRepository @Inject()(cassandraClient: CassandraClient) {

  private val keyspace = "parking_system"
  private val connection = cassandraClient.getSession(keyspace)

  private val parkingSpot = "reservation"

  private val reserveStatement: PreparedStatement = connection.prepare(s"INSERT INTO $parkingSpot(id, user_id, spot_id, status, start_time) values(?,?,?,?,?)")
  private val getStatement: PreparedStatement = connection.prepare(s"select * from $parkingSpot where user_id = ?")
  private val selectStatement: PreparedStatement = connection.prepare(s"select * from $parkingSpot where user_id = ? and id = ?")
  private val updateStatusStatement: PreparedStatement = connection.prepare(s"update reservation set status = ? where user_id = ? and id = ?  ")

  def reserve(reservation: Reservation): Unit = {
    import reservation._
    connection.execute(reserveStatement.bind(id, userId, spotId, status.toString, new Date(startTime * 1000)))
  }

  def get(userId: String, id: String): Option[Reservation] = {
    connection.execute(getStatement.bind(UUID.fromString(userId))).asScala.headOption.map(toReservation)
  }

  def get(userId: String): List[Reservation] = {
    connection.execute(getStatement.bind(UUID.fromString(userId))).asScala.map(toReservation).toList
  }

  def updateStatus(id: String, userId: String, status: ReservationStatus): Unit = {
    connection.execute(updateStatusStatement.bind(status.toString, userId, id))
  }

  def toReservation(row: Row): Reservation = {
    import row._
    val status = ReservationStatusFactory.getInstance(getString("status").trim)
    val startTime = getTimestamp("start_time").getTime
    val endTime = getTimestamp("end_time").getTime
    val totalCharges = getDouble("total_charges")
    Reservation(getUUID("id"), getUUID("user_id"), getUUID("spot_id"),status, startTime, Some(endTime), Some(totalCharges))
  }

}
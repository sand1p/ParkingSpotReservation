package com.ridecell.repositories

import java.sql.PreparedStatement
import java.util.UUID

import com.datastax.driver.core.Row
import com.ridecell.connections.{CassandraClient, PostgresClient}
import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}

@Singleton
class ParkingSpotRepository@Inject()(postgresClient: PostgresClient) {

  val connection = postgresClient.getConnection

  private val costPerHourStatement : PreparedStatement = connection.prepareStatement(s"select cost_per_hour from parking_spot where id = ?")

  def getPricePerHour(spotId: UUID): Double = {
    costPerHourStatement.setString(1, spotId.toString)
    val result = costPerHourStatement.executeQuery()
    result.getBigDecimal("cost_per_hour").doubleValue()
  }

  def getParkingSpots(): List[ParkingSpot] = {
    Nil
  }

  private def toParkingSpot(row: Row): ParkingSpot = {
    ParkingSpot(
      row.getInt("lat"),
      row.getInt("lng"),
      row.getInt("spotId"),
      row.getInt("pricePerHour")
    )
  }

}


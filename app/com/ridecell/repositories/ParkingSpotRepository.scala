package com.ridecell.repositories

import com.datastax.driver.core.Row
import com.ridecell.connections.CassandraClient
import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}

@Singleton
class ParkingSpotRepository@Inject()(cassandraClient: CassandraClient) {
  private val keyspace = "parkingsystem"
  private val parkingSpot = "parkingspot"
  private val session  = cassandraClient.getSession(keyspace)
  private val getPricePerHour = session.prepare(s"SELECT pricePerHour FROM $keyspace.$parkingSpot WHERE spotId = ? ALLOW FILTERING")
  private val getParkingSpotsQuery = session.prepare(s"SELECT * FROM $keyspace.$parkingSpot")

  def getPricePerHour(spotId: Int): Int = {
    session.execute(getPricePerHour.bind(spotId.asInstanceOf[Object])).one().getInt("pricePerHour")
  }

  def getParkingSpots(): List[ParkingSpot] = {
    import collection.JavaConverters._
    session.execute(getParkingSpotsQuery.bind()).asScala.toList.map( parkingSpot => toParkingSpot(parkingSpot))
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


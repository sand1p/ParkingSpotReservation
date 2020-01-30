package com.ridecell.repositories

import java.util.UUID

import com.datastax.driver.core.{PreparedStatement, Row}
import com.ridecell.connections.CassandraClient
import com.ridecell.models.ParkingSpot
import javax.inject.{Inject, Singleton}


@Singleton
class ParkingSpotRepository @Inject()(cassandraClient: CassandraClient) {

  private val keyspace = "parking_system"
  private val connection = cassandraClient.getSession(keyspace)

  private val parkingSpot = "parking_spot"
  private val selectAvailable: PreparedStatement = connection.prepare(s"select * from $parkingSpot")
  private val selectCostPerMinute: PreparedStatement = connection.prepare(s"select cost_per_minute from $parkingSpot where id = ?")
  private val updateStatus: PreparedStatement = connection.prepare(s"update $parkingSpot set is_available = ? where id = ? ")

  def getCostPerMinute(spotId: UUID): Option[Int] = {
    import scala.collection.JavaConverters._
    connection.execute(selectCostPerMinute.bind(spotId)).asScala.headOption.map(x => x.getInt("cost_per_minute"))
  }

  def getAvailable: List[ParkingSpot] = {
    import scala.collection.JavaConverters._
    connection.execute(selectAvailable.bind()).asScala.map(rowToParkingSpot).toList.filter(parkingSpot => parkingSpot.isAvailable)
  }

  private def rowToParkingSpot(row: Row): ParkingSpot =
    ParkingSpot(row.getUUID("id"),
      row.getDouble("lat"),
      row.getDouble("lng"),
      row.getInt("spot_number"),
      row.getInt("cost_per_minute"),
      row.getBool("is_available")
    )

  def allocate(spotId: UUID): Unit = {
    val availability = true
    updateAvailability(spotId, availability)
  }

  def deAllocate(spotId: String): Unit = {
    val availability = false
    updateAvailability(UUID.fromString(spotId), availability)
  }

  def updateAvailability(spotId: UUID, availability: Boolean) = {
    connection.execute(updateStatus.bind(availability.asInstanceOf[Object], spotId)).wasApplied()
  }


}


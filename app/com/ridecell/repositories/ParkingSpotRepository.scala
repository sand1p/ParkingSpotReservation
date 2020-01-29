package com.ridecell.repositories

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

  private val updateStatus: PreparedStatement = connection.prepare(s"update $parkingSpot set is_available = ? where id = ? ")


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

  def allocate(parkingSpot: ParkingSpot): Boolean = {
    import parkingSpot._
    // false will will be set to make parking spot unavailable for other bookings
    val isAvailable = false
    connection.execute(updateStatus.bind(isAvailable.asInstanceOf[Object], id)).wasApplied()
  }


}


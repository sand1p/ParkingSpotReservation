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
  private val selectAvailable: PreparedStatement = connection.prepare(s"select * from $parkingSpot where is_available = ?")

  def getAvailable: List[ParkingSpot] = {
    import scala.collection.JavaConverters._
    connection.execute(selectAvailable.bind(true.asInstanceOf[Object])).asScala.map(rowToParkingSpot).toList
  }

  private def rowToParkingSpot(row: Row): ParkingSpot =
    ParkingSpot(row.getUUID("id"),
      row.getDouble("lat"),
      row.getDouble("lng"),
      row.getInt("spot_number"),
      row.getInt("cost_per_minute")
    )

}


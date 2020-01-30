package stubs

import java.util.UUID

import com.ridecell.models.ParkingSpot
import com.ridecell.services.ParkingSpotService

class StubParkingSpotService extends ParkingSpotService(null) {
  override def getChargesPerMinute(spotId: UUID): Option[Int] = {
    Some(2)
  }

  override def search(lat: Double, lng: Double, radius: Double): List[ParkingSpot] = {
    List(ParkingSpot(UUID.fromString("8ed36cf0-41b8-11ea-88e0-f530e33335cb"), -23.23, 20.23, 3, 2, true))
  }

  override def allocate(parkingSpot: ParkingSpot): Unit = {
    Unit
  }

  override def getAvailable: List[ParkingSpot] = {
    List(ParkingSpot(UUID.fromString("8ed36cf0-41b8-11ea-88e0-f530e33335cb"), -23.23, 20.23, 3, 2, true))
  }

  override def deAllocate(spotId: String): Unit = {
    Unit
  }
}

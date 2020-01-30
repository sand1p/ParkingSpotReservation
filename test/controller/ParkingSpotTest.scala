package controller

import com.ridecell.controllers.ParkingSpotController
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.http.Status.OK
import play.api.test.FakeRequest
import play.api.test.Helpers._
import stubs.StubParkingSpotService

import scala.xml.Elem

class ParkingSpotTest extends PlaySpec with GuiceOneAppPerTest {

  "getAvailable" should {
    "return ok Response" in {
      val parkingSpotController = new ParkingSpotController(stubControllerComponents(), new StubParkingSpotService())
      val response = parkingSpotController.getAvailableParkingSpots.apply(FakeRequest(GET, "/v1/parkingSpots"))
      status(response) mustBe OK
    }

    "getAvailable" should {
      "return list of parking repositories as a Response" in {
        val parkingSpotController = new ParkingSpotController(stubControllerComponents(), new StubParkingSpotService())
        val response = parkingSpotController.getAvailableParkingSpots.apply(FakeRequest(GET, "/v1/parkingSpots"))
        status(response) mustBe OK
        val result: Elem = <response>
          <status>Success</status>
                 <parkingSpots>
                  <parkingSpot>
                    <lat>-23.23</lat>
                    <lng>20.23</lng>
                    <spotNumber>3</spotNumber>
                    <pricePerMinute>2</pricePerMinute>
                    <id>8ed36cf0-41b8-11ea-88e0-f530e33335cb</id>
                    <isAvailable>true</isAvailable>
                  </parkingSpot>
                 </parkingSpots>
        </response>
        contentAsString(response).trim mustBe result.toString().trim
      }
    }
  }
}
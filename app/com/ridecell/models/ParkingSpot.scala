package com.ridecell.models

import java.util.UUID

import scala.xml.Elem

/**
  *
  * @param id Unique id of parking_spot table
  * @param lat lattitude
  * @param lng lngitude
  * @param spotNumber
  * @param costPerMinute
  */

case class ParkingSpot(id: UUID, lat: Double, lng: Double, spotNumber: Int, costPerMinute: Int, isAvailable: Boolean) {
  def asXML(): Elem =
    <parkingSpot>
      <lat>{lat}</lat>
      <lng>{lng}</lng>
      <spotNumber>{spotNumber}</spotNumber>
      <pricePerMinute>{costPerMinute}</pricePerMinute>
      <id>{id}</id>
      <isAvailable>{isAvailable}</isAvailable>
    </parkingSpot>
}
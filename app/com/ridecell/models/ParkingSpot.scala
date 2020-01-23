package com.ridecell.models

import scala.xml.Elem


case class ParkingSpot(lat: Int, lng: Int, spotId: Int, pricePerHour: Int){
  def asXML(): Elem =
    <parkingSpot>
      <lat>{lat}</lat>
      <lng>{lng}</lng>
      <spotId>{spotId}</spotId>
      <pricePerHour>{pricePerHour}</pricePerHour>
    </parkingSpot>
}
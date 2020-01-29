package com.ridecell.models

import java.util.UUID

import scala.xml.NodeSeq


trait ReservationStatus

case object Active extends ReservationStatus {
  override def toString: String = "Active"
}

case object Completed extends ReservationStatus {
  override def toString: String = "Completed"
}

case object Reserved extends ReservationStatus {
  override def toString: String = "Reserved"
}

object ReservationStatusFactory {
  def get(status: String): ReservationStatus = {
    status.toLowerCase match {
      case "active" => Active
      case "completed" => Completed
      case "reserved" => Reserved
    }
  }
}

case class Reservation(id: UUID, userId: UUID, spotId: UUID, status: ReservationStatus, startTime: Long, endTime: Option[Long],totalCharges: Option[Double]){
  def asXML: NodeSeq =
    <reservation>
      <id>{id}</id>
      <userId>{userId}</userId>
      <spotId>{spotId}</spotId>
      <status>{status.toString}</status>
      <startTime>{startTime}</startTime>
      {endTime.map( endTime => <endTime>{endTime}</endTime>)}
      {totalCharges.map( charges => <totalCharges>{charges}</totalCharges>)}
  </reservation>
}

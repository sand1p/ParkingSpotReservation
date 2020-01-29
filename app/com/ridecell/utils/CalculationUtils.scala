package com.ridecell.utils

import java.util.Date

import javax.inject.Singleton

@Singleton
class CalculationUtils {

  /**
    * Calculate charges per minute based on number of hours
    *
    * @param costPerMinute
    * @param startTimeEpoch
    * @param endTimeEpoch
    * @return
    */
  def calculateTotalCharge(costPerMinute: Int, startTimeEpoch: Long, endTimeEpoch: Long): Double = {
    val numberOfMinutes = getDurationInMinutes(startTimeEpoch, endTimeEpoch)
    numberOfMinutes * costPerMinute
  }

  /**
    * Gives duration between 2 dates in minutes.
    *
    * @param startDateEpoch
    * @param endDateEpoch
    * @return
    */
  def getDurationInMinutes(startDateEpoch: Long, endDateEpoch: Long): Long = {
    val differenceInMillis = endDateEpoch - startDateEpoch
    convertMillisToMinutes(differenceInMillis)
  }

  /**
    * Convert number milliseconds to minutes
    *
    * @param millis
    * @return
    */
  def convertMillisToMinutes(millis: Long): Long = {
    millis / 60 * 1000
  }

}

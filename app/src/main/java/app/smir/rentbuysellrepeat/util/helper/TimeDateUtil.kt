package app.smir.rentbuysellrepeat.util.helper

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/

object TimeDateUtil {

 fun formatIsoDateWithSuffix(isoDateString: String?): String {
  if (isoDateString.isNullOrBlank()) {
   return ""
  }

  // 1. Parse the ISO 8601 string.
  // OffsetDateTime is a good choice because it handles the 'Z' (Zulu/UTC) time zone.
  val offsetDateTime: OffsetDateTime = try {
   OffsetDateTime.parse(isoDateString, DateTimeFormatter.ISO_DATE_TIME)
  } catch (e: Exception) {
   // Handle parsing exceptions (e.g., malformed string)
   e.printStackTrace()
   return ""
  }

  // 2. Format the date into the desired pattern.
  // The pattern "d'th' MMMM yyyy" will produce "4th August 2025" for a day of 4.
  // Note: The 'th' part needs a special logic because it changes for 1st, 2nd, 3rd.
  // Let's implement that logic below.
  val day = offsetDateTime.dayOfMonth
  val month = offsetDateTime.month.name // "AUGUST"
  val year = offsetDateTime.year.toString()

  // Create the day suffix (st, nd, rd, th)
  val daySuffix = getDayOfMonthSuffix(day)

  // Build the final string
  return "$day$daySuffix ${
   month.lowercase().replaceFirstChar { it.uppercase() }
  } $year"
 }

 /**
  * Returns the day of the month with the correct suffix (e.g., 1 -> "st", 2 -> "nd").
  */
 fun getDayOfMonthSuffix(day: Int): String {
  if (day in 11..13) {
   return "th"
  }
  return when (day % 10) {
   1 -> "st"
   2 -> "nd"
   3 -> "rd"
   else -> "th"
  }
 }
}
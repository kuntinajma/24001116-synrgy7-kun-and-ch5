package com.listing.movie.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtils {
    companion object{
        private val format = SimpleDateFormat("dd MMMM yyyy")
        fun formatDate(date: Date):String{
            return format.format(date)
        }
        fun parseDate(date: String): Date? {
            return format.parse(date)
        }
        fun getDate(year: Int, month: Int, day: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return calendar.time
        }
    }
}
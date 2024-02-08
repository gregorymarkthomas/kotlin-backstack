package com.gregorymarkthomas.backstackexample.model.repo

import com.gregorymarkthomas.backstackexample.model.util.CalendarHelper
import java.util.Calendar
import java.util.Date

class LocalDateTimeRepository : DateTimeRepositoryInterface {
    override fun getNowCalendar(): Calendar {
        val calendar = CalendarHelper.getNewCalendar()
        calendar.time = Date()
        return calendar
    }
}
package com.gregorymarkthomas.backstackexample.model.repo

import java.util.Calendar

interface DateTimeRepositoryInterface {
    fun getNowCalendar(): Calendar
}
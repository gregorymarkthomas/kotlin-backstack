package com.gregorymarkthomas.backstackexample.view

interface AViewInterface : BackStackListViewInterface {
    fun setDateView(dayOfMonth: Int, monthOfYear: String, year: Int)
    fun setTimeView(hours: String, minutes: String, seconds: String)
}

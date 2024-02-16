package com.gregorymarkthomas.backstackexample.view

interface ViewInterface {
    fun setViewName(name: String)
    fun setDateView(dayOfMonth: Int, monthOfYear: String, year: Int)
    fun setTimeView(hours: String, minutes: String, seconds: String)
    fun setBackstackListView(text: String)
}

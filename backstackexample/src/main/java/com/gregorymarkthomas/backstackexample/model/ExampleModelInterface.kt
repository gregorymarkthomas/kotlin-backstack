package com.gregorymarkthomas.backstackexample.model

import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import java.util.Calendar

interface ExampleModelInterface : ModelInterface {
    fun getNowCalendar(): Calendar
}
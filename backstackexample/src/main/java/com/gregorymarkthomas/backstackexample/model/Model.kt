package com.gregorymarkthomas.backstackexample.model

import com.gregorymarkthomas.backstackexample.model.repo.DateTimeRepositoryInterface
import java.util.Calendar

class Model(private val dateTimeRepo: DateTimeRepositoryInterface) : ExampleModelInterface {
    override fun getNowCalendar(): Calendar {
        return dateTimeRepo.getNowCalendar()
    }
}
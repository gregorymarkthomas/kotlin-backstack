package com.gregorymarkthomas.backstackexample.model

import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstackexample.model.repo.DateTimeRepositoryInterface
import java.util.Calendar

class Model(private val dateTimeRepo: DateTimeRepositoryInterface) : ModelInterface {
    override fun getNowCalendar(): Calendar {
        return dateTimeRepo.getNowCalendar()
    }
}
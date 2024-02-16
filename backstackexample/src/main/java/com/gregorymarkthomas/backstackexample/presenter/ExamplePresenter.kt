package com.gregorymarkthomas.backstackexample.presenter

import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstackexample.model.ExampleModelInterface
import com.gregorymarkthomas.backstackexample.model.util.CalendarHelper
import com.gregorymarkthomas.backstackexample.presenter.util.BackStackHelper
import com.gregorymarkthomas.backstackexample.view.ViewInterface
import java.util.Calendar

/**
 * Notes:
 * - The View that creates the Presenter will already be initialised/viewable before Presenter initialisation.
 * - There should be NO "Android" code in the PresenterInterface.
 * - Views should be as dumb as possible - let the Presenter do the work.
 * - Models should be used to get the data, but Presenter manages the data.
 * - Similar Views can share a Presenter (e.g. DayView, MonthView, YearView for a Calendar app),
 * but there should be a Presenter per feature.
 */
class ExamplePresenter(private val view: ViewInterface,
                       private val model: ExampleModelInterface,
                       private val backstack: BackStackInterface): ExamplePresenterInterface, OnResumePresenterInterface {

    init {
        updateDateTime()
        view.setBackstackListView(BackStackHelper.toString(backstack.getCurrentViewClasses()))
    }
    override fun onResume() {
        updateDateTime()
    }

    override fun onNowButtonPress() {
        updateDateTime()
    }

    private fun updateDateTime() {
        val calendar = model.getNowCalendar()
        view.setDateView(calendar.get(Calendar.DAY_OF_MONTH),
            CalendarHelper.getMonthString(calendar.get(Calendar.MONTH)),
            calendar.get(Calendar.YEAR))
        view.setTimeView(CalendarHelper.convert(calendar.get(Calendar.HOUR)),
            CalendarHelper.convert(calendar.get(Calendar.MINUTE)),
            CalendarHelper.convert(calendar.get(Calendar.SECOND)))
    }
}
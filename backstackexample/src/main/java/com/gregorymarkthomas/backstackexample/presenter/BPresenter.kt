package com.gregorymarkthomas.backstackexample.presenter

import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstackexample.presenter.util.BackStackHelper
import com.gregorymarkthomas.backstackexample.view.AView
import com.gregorymarkthomas.backstackexample.view.BViewInterface

/**
 * Notes:
 * - The View that creates the Presenter will already be initialised/viewable before Presenter initialisation.
 * - There should be NO "Android" code in the PresenterInterface.
 * - Views should be as dumb as possible - let the Presenter do the work.
 * - Models should be used to get the data, but Presenter manages the data.
 * - Similar Views can share a Presenter (e.g. DayView, MonthView, YearView for a Calendar app),
 * but there should be a Presenter per feature.
 */
class BPresenter(private val view: BViewInterface,
                 private val model: ModelInterface,
                 private val backstack: BackStackInterface): BPresenterInterface {

    init {
        view.setBackstackListView(BackStackHelper.toString(backstack.getCurrentViewClasses()))
    }

    override fun onGoToAButtonPress() {
        backstack.goTo(AView())
    }
}
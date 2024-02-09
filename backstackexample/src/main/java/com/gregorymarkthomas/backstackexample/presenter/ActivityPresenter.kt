package com.gregorymarkthomas.backstackexample.presenter

import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstackexample.view.AView
import com.gregorymarkthomas.backstackexample.view.BView
import com.gregorymarkthomas.backstackexample.view.CView

class ActivityPresenter(private val backstack: BackStackInterface): ActivityPresenterInterface {

    override fun onGoToButtonPress(position: Int) {
        when (position){
            0 ->  backstack.goTo(AView())
            1 ->  backstack.goTo(BView())
            2 ->  backstack.goTo(CView())
        }
    }

    override fun onClearToButtonPress(position: Int) {
        when (position){
            0 ->  backstack.clearTo(AView())
            1 ->  backstack.clearTo(BView())
            2 ->  backstack.clearTo(CView())
        }
    }

    override fun onGoBackButtonPress() {
        backstack.goBack()
    }
}
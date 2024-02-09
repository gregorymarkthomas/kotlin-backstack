package com.gregorymarkthomas.backstackexample.presenter

interface ActivityPresenterInterface {
    fun onGoToButtonPress(position: Int)
    fun onClearToButtonPress(position: Int)
    fun onGoBackButtonPress()
}
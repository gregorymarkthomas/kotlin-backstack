package com.gregorymarkthomas.backstackexample.presenter

interface PresenterInterface {
    fun onNowButtonPress()
    fun onGoToButtonPress(position: Int)
    fun onClearToButtonPress(position: Int)
    fun onGoBackButtonPress()
}
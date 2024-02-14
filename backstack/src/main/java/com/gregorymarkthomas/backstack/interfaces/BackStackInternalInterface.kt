package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView


internal interface BackStackInternalInterface {
    fun goTo(view: BackStackView, callback: BackStackViewCallback)
    fun clearTo(view: BackStackView, callback: BackStackViewCallback)
    fun goBack(callback: BackStackViewCallback): Boolean
    fun getMostRecentView(): BackStackView?
    fun getCurrentViewClasses(): List<String>
}
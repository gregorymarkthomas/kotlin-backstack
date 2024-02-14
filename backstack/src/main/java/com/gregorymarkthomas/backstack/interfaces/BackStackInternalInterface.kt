package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView


internal interface BackStackInternalInterface {
    fun goTo(view: BackStackView, callback: BackStackCallback)
    fun clearTo(view: BackStackView, callback: BackStackCallback)
    fun goBack(callback: BackStackCallback): Boolean
    fun getMostRecentView(): BackStackView?
    fun getCurrentViewClasses(): List<String>
}
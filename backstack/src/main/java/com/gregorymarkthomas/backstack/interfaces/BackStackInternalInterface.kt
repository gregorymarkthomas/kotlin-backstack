package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView


internal interface BackStackInternalInterface {
    fun goTo(view: BackStackView): BackStackView
    fun clearTo(view: BackStackView): BackStackView
    fun goBack(): BackStackView?
    fun getMostRecentView(): BackStackView?
    fun getCurrentViewClasses(): List<String>
}
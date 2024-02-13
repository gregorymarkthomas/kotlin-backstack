package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView

interface BackStackInterface {
    fun goTo(view: BackStackView)
    fun clearTo(view: BackStackView)
    fun goBack(): Boolean
    fun getMostRecentView(): BackStackView
    fun getCurrentViewClasses(): List<String>
}
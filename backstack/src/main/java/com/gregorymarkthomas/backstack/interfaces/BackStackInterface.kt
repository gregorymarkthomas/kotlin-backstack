package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView

interface BackStackInterface {
    fun goTo(view: BackStackView)
    fun resumeTo(viewClass: Class<out BackStackView>)
    fun clearTo(view: BackStackView)
    fun goBack()
    fun getMostRecentView(): BackStackView?
    fun getCurrentViewClasses(): List<String>
}
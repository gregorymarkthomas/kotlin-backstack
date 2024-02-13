package com.gregorymarkthomas.backstack

import com.gregorymarkthomas.backstack.interfaces.BackStackCallback
import com.gregorymarkthomas.backstack.interfaces.BackStackInternalInterface
import com.gregorymarkthomas.backstack.view.BackStackView

internal class BackStackManager private constructor(): BackStackInternalInterface {

    private var stack: BackStack

    init {
        stack = BackStack()
    }

    companion object {
        val instance: BackStackManager by lazy(::BackStackManager)
    }

    override fun goTo(view: BackStackView, callback: BackStackCallback) {
        stack.goTo(view, callback)
    }

    override fun clearTo(view: BackStackView, callback: BackStackCallback) {
        stack.clearTo(view, callback)
    }

    override fun goBack(callback: BackStackCallback): Boolean {
        return stack.goBack(callback)
    }

    override fun getMostRecentView(): BackStackView? {
        return stack.getMostRecentView()
    }

    override fun getCurrentViewClasses(): List<String> {
        return stack.getCurrentViewClasses()
    }
}
package com.gregorymarkthomas.backstack

import com.gregorymarkthomas.backstack.interfaces.BackStackInternalInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackViewCallback
import com.gregorymarkthomas.backstack.view.BackStackView

internal class BackStackManager private constructor(): BackStackInternalInterface {

    private var stack: BackStack

    init {
        stack = BackStack()
    }

    companion object {
        val instance: BackStackManager by lazy(::BackStackManager)
    }

    override fun goTo(view: BackStackView, callback: BackStackViewCallback) {
        stack.goTo(view, callback)
    }

    override fun clearTo(view: BackStackView, callback: BackStackViewCallback) {
        stack.clearTo(view, callback)
    }

    override fun goBack(callback: BackStackViewCallback): Boolean {
        return stack.goBack(callback)
    }

    override fun getMostRecentView(): BackStackView? {
        return stack.getMostRecentView()
    }

    override fun getCurrentViewClasses(): List<String> {
        return stack.getCurrentViewClasses()
    }
}
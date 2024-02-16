package com.gregorymarkthomas.backstack

import com.gregorymarkthomas.backstack.interfaces.BackStackInternalInterface
import com.gregorymarkthomas.backstack.view.BackStackView

internal class BackStackManager private constructor(): BackStackInternalInterface {

    private var stack: BackStack = BackStack()

    companion object {
        val instance: BackStackManager by lazy(::BackStackManager)
    }

    override fun goTo(view: BackStackView): BackStackView {
        return stack.goTo(view)
    }

    override fun clearTo(view: BackStackView): BackStackView {
        return stack.clearTo(view)
    }

    override fun goBack(): BackStackView? {
        return stack.goBack()
    }

    override fun getMostRecentView(): BackStackView? {
        return stack.getMostRecentView()
    }

    override fun getCurrentViewClasses(): List<String> {
        return stack.getCurrentViewClasses()
    }
}
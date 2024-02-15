package com.gregorymarkthomas.backstack

import com.gregorymarkthomas.backstack.interfaces.BackStackInternalInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackViewCallback
import com.gregorymarkthomas.backstack.view.BackStackView

/**
 * Each view requires a few Android-related items, plus control of the backstack, hence the input arguments.
 * This is not a singleton in case the app has multiple Activities - there will be one BackStack per Activity.
 */
class BackStack: BackStackInternalInterface {

    private var stack: MutableList<BackStackView> = mutableListOf()

    /********** public */
    /**
     * Always adds new instance of [view]; will replace if already exists.
     */
    override fun goTo(view: BackStackView, callback: BackStackViewCallback) {
        val index = indexOf(view::class.java)
        if(index != -1)
            stack = trim(stack, index)
        stack.add(view)
        callback.onCreate(view)
    }

    /**
     * Clears stack and makes [view] the only element.
     */
    override fun clearTo(view: BackStackView, callback: BackStackViewCallback) {
        stack = mutableListOf()
        stack.add(view)
        callback.onCreate(view)
    }

    override fun goBack(callback: BackStackViewCallback): Boolean {
        val success = try {
            if(getMostRecentViewIndex() != 0) {
                stack.removeAt(getMostRecentViewIndex())
                callback.onCreate(getMostRecentView()!!)
                true
            } else
                false
        } catch (e: ArrayIndexOutOfBoundsException) {
            false
        }

        return success
    }

    override fun getMostRecentView(): BackStackView? {
        val index = getMostRecentViewIndex()
        return if(index > -1)
            stack[index]
        else
            null
    }

    override fun getCurrentViewClasses(): List<String> {
        val classNames = ArrayList<String>(stack.size)
        for (view in stack) {
            classNames.add(view::class.simpleName!!)
        }
        return classNames
    }

    /********** private */
    /**
     * Looks in the backstack for the given View
     */
    private fun indexOf(view: Class<out BackStackView>): Int {
        return stack.indexOfLast { it::class.java == view }
    }

    /**
     * This removes views from the top of the stack, INCLUDING the the requested index.
     */
    private fun trim(stack: MutableList<BackStackView>, index: Int): MutableList<BackStackView> {
        return stack.subList(0, index)
    }

    /**
     * Retrieves the index for the last item in the stack.
     */
    private fun getMostRecentViewIndex(): Int {
        return stack.size - 1
    }
}
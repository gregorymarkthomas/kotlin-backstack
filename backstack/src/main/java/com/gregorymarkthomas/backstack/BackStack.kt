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
     * Either finds the existing instance of the requested viewClass and uses it, or it adds a new instance if it does not yet exist.
     */
    override fun goTo(view: BackStackView, callback: BackStackViewCallback) {
        val index = indexOf(view)
        if(index == -1) {
            stack.add(view)
            callback.onCreate(view)
        } else {
            stack = trimToExisting(stack, index)
            callback.onResume(view)
        }
    }

    override fun clearTo(view: BackStackView, callback: BackStackViewCallback) {
        val index = indexOf(view)
        if(index == -1) {
            stack = mutableListOf()
            stack.add(view)
            callback.onCreate(view)
        } else {
            val existing = stack[index]
            stack = mutableListOf()
            stack.add(existing)
            callback.onResume(existing)
        }
    }

    override fun goBack(callback: BackStackViewCallback): Boolean {
        val success = try {
            if(getMostRecentViewIndex() != 0) {
                stack.removeAt(getMostRecentViewIndex())
                true
            } else
                false
        } catch (e: ArrayIndexOutOfBoundsException) {
            false
        }
        callback.onResume(getMostRecentView()!!)
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
    private fun indexOf(view: BackStackView): Int {
        return stack.indexOfLast { it::class.java == view::class.java }
    }

    /**
     * This removes views from the top of the stack to make the requested view the most recent view
     */
    private fun trimToExisting(stack: MutableList<BackStackView>, newViewIndex: Int): MutableList<BackStackView> {
        return stack.subList(0, newViewIndex + 1)
    }

    /**
     * Retrieves the index for the last item in the stack.
     */
    private fun getMostRecentViewIndex(): Int {
        return stack.size - 1
    }
}
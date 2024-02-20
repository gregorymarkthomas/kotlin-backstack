package com.gregorymarkthomas.backstack.internal

import com.gregorymarkthomas.backstack.internal.interfaces.BackStackInternalInterface
import com.gregorymarkthomas.backstack.view.BackStackView

internal class BackStackInternal: BackStackInternalInterface {

    private var stack: MutableList<BackStackView> = mutableListOf()

    /********** public */
    /**
     * Brings input view to top of stack - always adds new instance of [view],
     * and will replace if already exists.
     */
    override fun goTo(view: BackStackView): BackStackView {
        val index = indexOf(view::class.java)
        if(index != -1)
            stack = trim(stack, index)
        stack.add(view)
        return view
    }

    /**
     * Resumes existing requested [viewClass], unless not found.
     */
    override fun resumeTo(viewClass: Class<out BackStackView>): BackStackView? {
        var view: BackStackView? = null
        val index = indexOf(viewClass)
        if(index != -1) {
            stack = trim(stack, index + 1)
            view = getMostRecentView()
        }
        return view
    }

    /**
     * Clears stack and makes [view] the only element.
     */
    override fun clearTo(view: BackStackView): BackStackView {
        stack = mutableListOf()
        stack.add(view)
        return view
    }


    /**
     * Destroys latest view and retrieves the second-from-most-recent view.
     */
    override fun goBack(): BackStackView? {
        return try {
            if(getMostRecentViewIndex() != 0) {
                stack.removeAt(getMostRecentViewIndex())
                getMostRecentView()!!
            } else
                null
        } catch (e: ArrayIndexOutOfBoundsException) {
            null
        }
    }

    /**
     * Retrieves the most-recent view in the stack.
     */
    override fun getMostRecentView(): BackStackView? {
        val index = getMostRecentViewIndex()
        return if(index > -1)
            stack[index]
        else
            null
    }

    /**
     * Returns the stack as a list of class names.
     */
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
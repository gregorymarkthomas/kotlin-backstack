package com.gregorymarkthomas.backstack.view

import android.view.ViewGroup
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface

abstract class BackStackView {

    private val tag = "BackStackView"

    /********** public */
    var view: ViewGroup? = null
        get() {
            return field ?: throw NullPointerException("'view' object has not yet been drawn. Use initialise() on the BackStackView.")
        }


    fun inflate(context: AndroidContextInterface): ViewGroup {
        view = createLayout(context)
        return view!!
    }

    protected abstract fun getTag(): String
    protected abstract fun getLayout(): Int
    abstract fun onCreate(backstack: BackStackInterface, model: ModelInterface,
                          context: AndroidContextInterface)

    abstract fun onResume(context: AndroidContextInterface)

    /********** private */
    private fun createLayout(context: AndroidContextInterface): ViewGroup {
        val parent = ViewGroup.inflate(context.getContext(), getLayout(), null)
        parent.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        parent.tag = getTag()
        return parent as ViewGroup
    }
}

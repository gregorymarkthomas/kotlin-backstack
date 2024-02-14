package com.gregorymarkthomas.backstack.view

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface

abstract class BackStackView {

    private val tag = "BackStackView"

    /********** public */
    var view: BackStackLayout? = null
        get() {
            return field ?: throw NullPointerException("'view' object has not yet been drawn. Use initialise() on the BackStackView.")
        }


    fun inflate(context: AndroidContextInterface): BackStackLayout {
        view = createLayout(context)
        return view!!
    }

    protected abstract fun getTag(): String
    protected abstract fun getLayout(): Int
    abstract fun onViewInitialised(backstack: BackStackInterface, model: ModelInterface,
                                             context: AndroidContextInterface)

    /********** private */
    private fun createLayout(context: AndroidContextInterface): BackStackLayout {
        val parent = ConstraintLayout.inflate(context.getContext(), getLayout(), null)
        /** Expand 'this' ConstraintLayout to use all of the usable space. **/
        parent.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        parent.tag = getTag()
        return parent as BackStackLayout
    }
}

package com.gregorymarkthomas.backstack.view

import androidx.constraintlayout.widget.ConstraintLayout
import android.view.ViewGroup
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface

abstract class BackStackView {

    /********** public */
    var view: ConstraintLayout? = null
        get() {
            return field ?: throw NullPointerException("'view' object has not yet been drawn. Use initialise() on the BackStackView.")
        }

    /**
     * TODO() - edit this description as function has changed since written
     * The <merge> tag has been used in each view that uses the BackStackView.
     * This is because BackStackView already subclasses ConstraintLayout.
     *      If month_view.xml also has a ConstraintLayout as the root, then there will be too many ViewGroups.
     *      We now use <merge> to use the higher level ViewGroup.
     */
    fun initialise(context: AndroidContextInterface): ConstraintLayout {
        view = createLayout(context)
        return view!!
    }

    /**
     * Manually called by the Activity in onViewChanged()
     */
    fun onInitialised(backstack: BackStackInterface, model: ModelInterface,
                      context: AndroidContextInterface) {
        onViewInitialised(backstack, model, context)
    }

    /********** protected */
    protected abstract fun getTag(): String
    protected abstract fun getLayout(): Int
    protected abstract fun onViewInitialised(backstack: BackStackInterface, model: ModelInterface,
                                             context: AndroidContextInterface)

    /********** private */
    private fun createLayout(context: AndroidContextInterface): ConstraintLayout {
        val layout = ConstraintLayout(context.getContext())
        ConstraintLayout.inflate(context.getContext(), getLayout(), layout)

        /** Expand 'this' ConstraintLayout to use all of the usable space. **/
        layout.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.tag = getTag()
        return layout
    }
}

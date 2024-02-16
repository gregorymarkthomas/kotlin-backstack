package com.gregorymarkthomas.backstack

import android.view.ViewGroup
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.internal.BackStackManager
import com.gregorymarkthomas.backstack.view.BackStackView

class BackStack(private val activity: ActivityInterface): BackStackInterface {

    fun onCreate() {
        val recent = getMostRecentView()
        if(recent == null)
            goTo(activity.getInitialView())
        else
            resumeTo(recent::class.java)
    }

    override fun goTo(view: BackStackView) {
        onViewChanged(BackStackManager.instance.goTo(view))
    }

    override fun resumeTo(viewClass: Class<out BackStackView>) {
        val view = BackStackManager.instance.resumeTo(viewClass)
        if(view != null)
            onViewResumed(view)
    }

    override fun clearTo(view: BackStackView) {
        onViewChanged(BackStackManager.instance.clearTo(view))
    }

    override fun goBack() {
        val view = BackStackManager.instance.goBack()
        if (view != null)
            onViewChanged(view)
    }

    override fun getMostRecentView(): BackStackView? {
        return BackStackManager.instance.getMostRecentView()
    }

    override fun getCurrentViewClasses(): List<String> {
        return BackStackManager.instance.getCurrentViewClasses()
    }

    private fun onViewChanged(view: BackStackView) {
        activity.removeAllViews()
        activity.addView(view.inflate(activity))
        view.onCreate(this, activity.getModel(), activity)
    }

    private fun onViewResumed(view: BackStackView) {
        activity.removeAllViews()
        activity.addView(view.inflate(activity))
        view.onResume(activity)
    }

    interface ActivityInterface: AndroidContextInterface {
        fun getInitialView(): BackStackView
        fun addView(view: ViewGroup)
        fun removeAllViews()
        fun getModel(): ModelInterface
    }
}
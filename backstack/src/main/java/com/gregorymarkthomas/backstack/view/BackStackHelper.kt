package com.gregorymarkthomas.backstack.view

import com.gregorymarkthomas.backstack.BackStackManager
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackViewCallback
import com.gregorymarkthomas.backstack.interfaces.ModelInterface

class BackStackHelper(private val activity: ActivityInterface): BackStackInterface, BackStackViewCallback {

    fun onCreate() {
        val recent = getMostRecentView()
        if(recent == null)
            goTo(activity.getInitialView())
        else
            goTo(recent)
    }

    fun onBackPressed() {
        BackStackManager.instance.goBack(this)
    }

    override fun onCreate(backstackView: BackStackView) {
        activity.removeAllViews()
        activity.addView(backstackView.inflate(activity))
        backstackView.onCreate(this, activity.getModel(), activity)
    }

    override fun onResume(backstackView: BackStackView) {
        activity.removeAllViews()
        activity.addView(backstackView.inflate(activity))
        backstackView.onResume(activity)
    }

    override fun goTo(view: BackStackView) {
        BackStackManager.instance.goTo(view, this)
    }

    override fun clearTo(view: BackStackView) {
        BackStackManager.instance.clearTo(view, this)
    }

    override fun goBack(): Boolean {
        return BackStackManager.instance.goBack(this)
    }

    override fun getMostRecentView(): BackStackView? {
        return BackStackManager.instance.getMostRecentView()
    }

    override fun getCurrentViewClasses(): List<String> {
        return BackStackManager.instance.getCurrentViewClasses()
    }

    interface ActivityInterface: AndroidContextInterface {
        fun getInitialView(): BackStackView
        fun addView(view: BackStackLayout)
        fun removeAllViews()
        fun getModel(): ModelInterface
    }
}
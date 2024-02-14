package com.gregorymarkthomas.backstack.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gregorymarkthomas.backstack.BackStackManager
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackCallback
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface

abstract class BackstackActivity : AppCompatActivity(), BackStackInterface, BackStackCallback,
    AndroidContextInterface {

    abstract fun getInitialView(): BackStackView
    abstract fun addView(view: BackStackLayout)
    abstract fun removeAllViews()
    abstract fun getModel(): ModelInterface

    /**
     * When the Activity view is ready, we will attach the first BackStackView.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recent = getMostRecentView()
        if(recent == null)
            goTo(getInitialView())
        else
            goTo(recent)
    }

    /**
     * When the BackStackView has been added to MainActivity, we can say it has been initialised.
     */
    override fun onViewChanged(backstackView: BackStackView) {
        removeAllViews()
        val view = backstackView.inflate(this)
        addView(view)
        backstackView.onViewInitialised(this, getModel(), this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        BackStackManager.instance.goBack(this)
    }

    /********* BackStack - these can be called by Presenters via an interface */
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

    override fun getContext(): Context {
        return this
    }
}
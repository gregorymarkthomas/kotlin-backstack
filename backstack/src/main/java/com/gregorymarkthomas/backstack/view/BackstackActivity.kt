package com.gregorymarkthomas.backstack.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gregorymarkthomas.backstack.BackStack
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackCallback
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import java.io.Serializable

abstract class BackstackActivity : AppCompatActivity(), BackStackInterface, BackStackCallback,
    AndroidContextInterface {

    private lateinit var backstack: BackStack

    companion object {
        const val INITIAL_VIEW_EXTRA = "initial_view_extra"
    }

    abstract fun getInitialView(): BackStackView
    abstract fun addView(view: ConstraintLayout)
    abstract fun removeAllViews()
    abstract fun getModel(): ModelInterface

    /**
     * When the Activity view is ready, we will attach the first BackStackView.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backstack = BackStack(this@BackstackActivity, getInitialView())
    }

    /**
     * When the BackStackView has been added to MainActivity, we can say it has been initialised.
     */
    override fun onViewChanged(backstackView: BackStackView) {
        removeAllViews()
        val view = backstackView.inflate(this)
        addView(view)
        backstackView.onInitialised(this@BackstackActivity, getModel(), this@BackstackActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backstack.goBack()
    }

    /********* BackStack - these can be called by Presenters via an interface */
    override fun goTo(view: BackStackView) {
        backstack.goTo(view)
    }

    override fun clearTo(view: BackStackView) {
        backstack.clearTo(view)
    }

    override fun goBack(): Boolean {
        return backstack.goBack()
    }

    override fun getMostRecentView(): BackStackView {
        return backstack.getMostRecentView()
    }

    override fun getCurrentViewClasses(): List<String> {
        return backstack.getCurrentViewClasses()
    }

    override fun getContext(): Context {
        return this
    }

    /********** private */
    inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }
}
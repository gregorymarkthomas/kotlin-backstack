package com.gregorymarkthomas.backstackexample

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.util.BackStackHelper
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstackexample.databinding.ActivityMainBinding
import com.gregorymarkthomas.backstackexample.model.Model
import com.gregorymarkthomas.backstackexample.model.repo.LocalDateTimeRepository
import com.gregorymarkthomas.backstackexample.presenter.ActivityPresenter
import com.gregorymarkthomas.backstackexample.presenter.ActivityPresenterInterface
import com.gregorymarkthomas.backstackexample.view.AView

/**
 * Solution to spinner call to onItemSelected() after orientation changes is thanks to
 * https://itecnote.com/tecnote/android-spinner-onitemselected-called-multiple-times-after-screen-rotation/
 */
class MainActivity : AppCompatActivity(), BackStackHelper.ActivityInterface, View.OnClickListener {
    private lateinit var presenter: ActivityPresenterInterface
    private lateinit var model: ModelInterface
    private lateinit var binding: ActivityMainBinding
    private val backstacker = BackStackHelper(this)
    private var isGoToSpinnerSelected: Boolean = false
    private var isClearToSpinnerSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goBackButton.setOnClickListener(this)
        setupGoToSpinner(getContext())
        setupClearToSpinner(getContext())

        this.model = Model(LocalDateTimeRepository())
        this.presenter = ActivityPresenter(backstacker)

        backstacker.onCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backstacker.onBackPressed()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.goBackButton -> presenter.onGoBackButtonPress()
        }
    }

    override fun getInitialView(): BackStackView {
        return AView()
    }

    override fun addView(view: ViewGroup) {
        binding.container.addView(view)
    }

    override fun removeAllViews() {
        binding.container.removeAllViews()
    }

    override fun getModel(): ModelInterface {
        return model
    }

    override fun getContext(): Context {
        return this
    }

    private fun setupGoToSpinner(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.views,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.goToButton.adapter = adapter
            binding.goToButton.setSelection(0, false)
            binding.goToButton.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    isGoToSpinnerSelected = true
                    return false
                }
            })
            binding.goToButton.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(isGoToSpinnerSelected)
                        presenter.onGoToButtonPress(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun setupClearToSpinner(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.views,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.clearToButton.adapter = adapter
            binding.clearToButton.setSelection(0, false)
            binding.clearToButton.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    isClearToSpinnerSelected = true
                    return false
                }
            })
            binding.clearToButton.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(isClearToSpinnerSelected)
                        presenter.onClearToButtonPress(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}
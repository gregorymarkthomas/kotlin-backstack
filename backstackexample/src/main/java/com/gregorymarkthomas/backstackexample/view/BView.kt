package com.gregorymarkthomas.backstackexample.view

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstackexample.R
import com.gregorymarkthomas.backstackexample.databinding.ViewBinding
import com.gregorymarkthomas.backstackexample.presenter.Presenter
import com.gregorymarkthomas.backstackexample.presenter.PresenterInterface

class BView: BackStackView(), ViewInterface, View.OnClickListener {
    private lateinit var presenter: PresenterInterface
    private lateinit var binding: ViewBinding

    /********** public */
    override fun getTag(): String = "BView"

    override fun getLayout(): Int = R.layout.view

    override fun onViewInitialised(backstack: BackStackInterface, model: ModelInterface,
                                   context: AndroidContextInterface) {
        binding = ViewBinding.bind(view!!)
        binding.updateDateTimeButton.setOnClickListener(this)
        binding.pageTextView.text = "B"

        setupGoToSpinner(context.getContext())
        setupClearToSpinner(context.getContext())

        /** This should be last. **/
        this.presenter = Presenter(this, model, backstack)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.updateDateTimeButton -> presenter.onNowButtonPress()
        }
    }

    override fun setDateView(dayOfMonth: Int, monthOfYear: String, year: Int) {
        binding.dateTextView.text = "$dayOfMonth $monthOfYear $year"
    }

    override fun setTimeView(hours: String, minutes: String, seconds: String) {
        binding.timeTextView.text = "$hours:$minutes:$seconds"
    }

    override fun setBackstackListView(text: String) {
        binding.backstackListTextView.text = text
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
            binding.goToButton.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            binding.clearToButton.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    presenter.onClearToButtonPress(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}
package com.gregorymarkthomas.backstackexample.view

import android.view.View
import android.view.View.OnClickListener
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstackexample.R
import com.gregorymarkthomas.backstackexample.databinding.ViewBinding
import com.gregorymarkthomas.backstackexample.presenter.Presenter
import com.gregorymarkthomas.backstackexample.presenter.PresenterInterface

class AView: BackStackView(), ViewInterface, OnClickListener {
    private lateinit var presenter: PresenterInterface
    private lateinit var binding: ViewBinding

    /********** public */
    override fun getTag(): String = "AView"

    override fun getLayout(): Int = R.layout.view

    override fun onViewInitialised(backstack: BackStackInterface, model: ModelInterface,
                                   context: AndroidContextInterface) {
        binding = ViewBinding.bind(view!!)
        binding.updateDateTimeButton.setOnClickListener(this)
        binding.pageTextView.text = "A"

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
}
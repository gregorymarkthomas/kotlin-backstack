package com.gregorymarkthomas.backstackexample.view

import android.view.View
import android.view.View.OnClickListener
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstackexample.R
import com.gregorymarkthomas.backstackexample.databinding.AViewBinding
import com.gregorymarkthomas.backstackexample.presenter.APresenter
import com.gregorymarkthomas.backstackexample.presenter.APresenterInterface

class AView: BackStackView(), AViewInterface, OnClickListener {
    private lateinit var presenter: APresenterInterface
    private lateinit var binding: AViewBinding

    /********** public */
    override fun getTag(): String = "AView"

    override fun getLayout(): Int = R.layout.a_view

    override fun onViewInitialised(backstack: BackStackInterface, model: ModelInterface,
                                   context: AndroidContextInterface) {
        binding = AViewBinding.bind(view!!)
        binding.updateDateTimeButton.setOnClickListener(this)
        binding.goToBButton.setOnClickListener(this)
        /** This should be last. **/
        this.presenter = APresenter(this, model, backstack)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.updateDateTimeButton -> presenter.onNowButtonPress()
            R.id.goToBButton -> presenter.onGoToBButtonPress()
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
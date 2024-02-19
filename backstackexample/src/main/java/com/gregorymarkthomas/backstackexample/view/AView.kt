package com.gregorymarkthomas.backstackexample.view

import android.view.View
import android.view.View.OnClickListener
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstackexample.R
import com.gregorymarkthomas.backstackexample.databinding.ViewBinding
import com.gregorymarkthomas.backstackexample.model.ExampleModelInterface
import com.gregorymarkthomas.backstackexample.presenter.ExamplePresenter
import com.gregorymarkthomas.backstackexample.presenter.ExamplePresenterInterface

class AView: BackStackView(), ViewInterface, OnClickListener {
    private lateinit var presenter: ExamplePresenterInterface
    private lateinit var binding: ViewBinding

    /********** public */
    override fun getLayout(): Int = R.layout.view

    override fun onCreate(backstack: BackStackInterface, model: ModelInterface,
                          context: AndroidContextInterface) {
        binding = ViewBinding.bind(view!!)
        binding.updateDateTimeButton.setOnClickListener(this)

        /** This should be last as Presenter will likely use views defined above. **/
        this.presenter = ExamplePresenter("A", this, model as ExampleModelInterface, backstack)
    }

    override fun onResume(context: AndroidContextInterface) {
        binding = ViewBinding.bind(view!!)
        this.presenter.onResume()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.updateDateTimeButton -> presenter.onNowButtonPress()
        }
    }

    override fun setViewName(name: String) {
        binding.pageTextView.text = name
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
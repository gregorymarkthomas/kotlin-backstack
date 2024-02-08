package com.gregorymarkthomas.backstackexample.view

import android.view.View
import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstackexample.R
import com.gregorymarkthomas.backstackexample.databinding.BViewBinding
import com.gregorymarkthomas.backstackexample.presenter.BPresenter
import com.gregorymarkthomas.backstackexample.presenter.BPresenterInterface

class BView: BackStackView(), BViewInterface, View.OnClickListener {
    private lateinit var presenter: BPresenterInterface
    private lateinit var binding: BViewBinding

    /********** public */
    override fun getTag(): String = "BView"

    override fun getLayout(): Int = R.layout.b_view

    override fun onViewInitialised(backstack: BackStackInterface, model: ModelInterface,
                                   context: AndroidContextInterface) {
        binding = BViewBinding.bind(view!!)
        binding.goToAButton.setOnClickListener(this)
        /** This should be last. **/
        this.presenter = BPresenter(this, model, backstack)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.goToAButton -> presenter.onGoToAButtonPress()
        }
    }

    override fun setBackstackListView(text: String) {
        binding.backstackListTextView.text = text
    }
}
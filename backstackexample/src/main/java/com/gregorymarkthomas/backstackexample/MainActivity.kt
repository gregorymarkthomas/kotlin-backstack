package com.gregorymarkthomas.backstackexample

import android.os.Bundle
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackLayout
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstack.view.BackstackActivity
import com.gregorymarkthomas.backstackexample.databinding.ActivityMainBinding
import com.gregorymarkthomas.backstackexample.model.Model
import com.gregorymarkthomas.backstackexample.model.repo.LocalDateTimeRepository
import com.gregorymarkthomas.backstackexample.view.AView

class MainActivity : BackstackActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: ModelInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = Model(LocalDateTimeRepository())
        super.onCreate(savedInstanceState)
    }

    override fun getInitialView(): BackStackView {
        return AView()
    }

    override fun addView(view: BackStackLayout) {
        binding.root.addView(view)
    }

    override fun removeAllViews() {
        binding.root.removeAllViews()
    }

    override fun getModel(): ModelInterface {
        return model
    }
}
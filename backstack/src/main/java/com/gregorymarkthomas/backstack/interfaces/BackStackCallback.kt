package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView

interface BackStackCallback {
    fun onViewChanged(backstackView: BackStackView)
}
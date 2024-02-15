package com.gregorymarkthomas.backstack.interfaces

import com.gregorymarkthomas.backstack.view.BackStackView

interface BackStackViewCallback {
    fun onCreate(backstackView: BackStackView)
}
package com.gregorymarkthomas.backstack.view

import com.gregorymarkthomas.backstack.interfaces.AndroidContextInterface
import com.gregorymarkthomas.backstack.interfaces.BackStackInterface
import com.gregorymarkthomas.backstack.interfaces.ModelInterface

class BView(): BackStackView() {

    /********** public */
    override fun getTag(): String = "AView"

    override fun getLayout(): Int = androidx.appcompat.R.layout.abc_action_bar_title_item

    override fun onCreate(backstack: BackStackInterface, model: ModelInterface, context: AndroidContextInterface) {
        /** Do nothing **/
    }

    override fun onResume(context: AndroidContextInterface) {
        /** Do nothing **/
    }
}
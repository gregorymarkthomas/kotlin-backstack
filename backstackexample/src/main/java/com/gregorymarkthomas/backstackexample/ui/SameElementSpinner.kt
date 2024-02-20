package com.gregorymarkthomas.backstackexample.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

class SameElementSpinner(context: Context, attrs: AttributeSet?) : AppCompatSpinner(context, attrs) {

    var listener: OnItemSelectedListener? = null

    override fun setSelection(position: Int) {
        super.setSelection(position)
        if (position == selectedItemPosition)
            listener!!.onItemSelected(this, selectedView, position, selectedItemId)
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        this.listener = listener
    }
}
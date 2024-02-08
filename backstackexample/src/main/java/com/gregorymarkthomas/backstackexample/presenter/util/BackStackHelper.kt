package com.gregorymarkthomas.backstackexample.presenter.util

internal class BackStackHelper {
    companion object {
        fun toString(currentViewClasses: List<String>): String {
            val builder = StringBuilder()
            builder.appendLine("Current backstack:")
            for (item: String in currentViewClasses) {
                builder.appendLine(item)
            }
            return builder.toString()
        }
    }
}
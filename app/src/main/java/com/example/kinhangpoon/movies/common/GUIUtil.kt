package com.example.kinhangpoon.movies.common

import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager

class GUIUtil{
    companion object {
        fun hideSoftKeyboard(view: View){
            if (view != null){
                val context = view.context
                val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val binder: IBinder = view.windowToken
                inputMethodManager.hideSoftInputFromWindow(binder, InputMethodManager.RESULT_UNCHANGED_SHOWN)
            }
        }
    }
}
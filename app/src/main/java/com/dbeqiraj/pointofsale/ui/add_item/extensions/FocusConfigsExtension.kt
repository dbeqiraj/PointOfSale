package com.dbeqiraj.pointofsale.ui.add_item.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.dbeqiraj.pointofsale.ui.add_item.AddItemActivity
import kotlinx.android.synthetic.main.content_add_item.*
import android.view.MotionEvent

internal fun AddItemActivity.focusConfigs() {
    imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    name.setOnFocusChangeListener(onFocusChangeListener())
    category.setOnTouchListener(onTouchListener())
    vat.setOnFocusChangeListener(onFocusChangeListener())
    barcode.setOnFocusChangeListener(onFocusChangeListener())
    price.setOnFocusChangeListener(onFocusChangeListener())
    cost.setOnFocusChangeListener(onFocusChangeListener())
    name.requestFocus()
}

private fun AddItemActivity.onFocusChangeListener(): View.OnFocusChangeListener{
    return View.OnFocusChangeListener { view, hasFocus ->
        if (!view.hasFocus() ) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

private fun AddItemActivity.onTouchListener(): View.OnTouchListener {
    return View.OnTouchListener{ view: View, motionEvent: MotionEvent ->
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        false
    }
}

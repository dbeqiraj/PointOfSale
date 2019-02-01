package com.dbeqiraj.pointofsale.ui.report_by_item.extensions

import android.view.View
import android.view.animation.TranslateAnimation

internal fun changeVisibility(checked: Boolean, view: View) : TranslateAnimation{
    val animate = if (checked) {
        TranslateAnimation(view.width.toFloat(), 0F, 0F, 0F)
    } else {
        TranslateAnimation(0F, view.width.toFloat(), 0F, 0F)
    }
    animate.setDuration(500)
    animate.setFillAfter(true)
    view.startAnimation(animate)
    if (checked) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
    return animate
}
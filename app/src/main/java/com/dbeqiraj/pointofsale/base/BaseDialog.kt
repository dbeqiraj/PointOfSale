package com.dbeqiraj.pointofsale.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager

abstract class BaseDialog(context: Context) {

    protected var mContext: Context = context
    lateinit var dialog: Dialog

    init {
        createDialog()
    }

    protected fun createDialog(){
        dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(getContentView())
        val window = dialog.window!!
        val wlp = window.attributes
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.attributes = wlp
        dialog.show()
    }

    protected abstract fun onViewReady()

    protected abstract fun getContentView(): Int

}
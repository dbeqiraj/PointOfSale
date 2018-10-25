package com.dbeqiraj.pointofsale.base.extensions

import android.app.ProgressDialog
import com.dbeqiraj.pointofsale.base.BaseActivity


fun BaseActivity.showDialog(message: String){
    mProgressDialog = ProgressDialog(this)
    mProgressDialog.setMessage(message)
    mProgressDialog.show()
}

fun BaseActivity.hideDialog(){
    if (mProgressDialog.isShowing) {
        mProgressDialog.dismiss()
    }
}
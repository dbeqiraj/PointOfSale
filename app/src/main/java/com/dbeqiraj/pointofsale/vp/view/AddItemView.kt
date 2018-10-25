package com.dbeqiraj.pointofsale.vp.view

interface AddItemView : CartView {
    fun onShowDialog()
    fun onHideDialog()
    fun onShowToast(message: String)
}
package com.dbeqiraj.pointofsale.vp.view

interface HomeView : CartView {
    fun setupNavDrawer()
    fun onShowToast(message: String)
}
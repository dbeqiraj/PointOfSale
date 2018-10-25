package com.dbeqiraj.pointofsale.vp.presenter

import android.arch.lifecycle.LiveData
import com.dbeqiraj.pointofsale.base.BasePresenter
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.vp.view.CartView
import javax.inject.Inject

class CategoryPresenter @Inject constructor() : BasePresenter<CartView>() {

    @Inject
    protected lateinit var db: PosDatabase

    fun getCategories(): LiveData<MutableList<Category>> = db.categoryDao().getCategories()

    fun getCategoryByName(name: String): LiveData<Category> = db.categoryDao().getCategoryName(name)

}
package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.AddCategoryView
import com.dbeqiraj.pointofsale.vp.view.AddItemView
import com.dbeqiraj.pointofsale.vp.view.CartView
import dagger.Module
import dagger.Provides

@Module
class AddCategoryModule(view: AddCategoryView) {

    val mView = view

    @PerActivity
    @Provides
    fun provideView(): AddCategoryView = mView
}
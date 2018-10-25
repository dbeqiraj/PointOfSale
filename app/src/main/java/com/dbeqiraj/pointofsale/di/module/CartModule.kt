package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.CartView
import dagger.Module
import dagger.Provides

@Module
class CartModule(view: CartView) {

    val mView = view

    @PerActivity
    @Provides
    fun provideView(): CartView = mView
}
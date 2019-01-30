package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.CartView
import dagger.Module
import dagger.Provides

@Module
class CartModule(val view: CartView) {
    @PerActivity
    @Provides
    fun provideView(): CartView = view
}
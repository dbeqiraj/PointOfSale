package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.CartView
import com.dbeqiraj.pointofsale.vp.view.CurrentSaleView
import dagger.Module
import dagger.Provides

@Module
class NewReceiptModule(view: CurrentSaleView) {

    val mView = view

    @PerActivity
    @Provides
    fun provideView(): CartView = mView
}
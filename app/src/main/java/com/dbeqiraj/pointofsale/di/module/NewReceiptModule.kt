package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.CartView
import com.dbeqiraj.pointofsale.vp.view.CurrentSaleView
import dagger.Module
import dagger.Provides

@Module
class NewReceiptModule(val view: CurrentSaleView) {
    @PerActivity
    @Provides
    fun provideView(): CartView = view
}
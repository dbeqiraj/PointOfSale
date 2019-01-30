package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.CartView
import com.dbeqiraj.pointofsale.vp.view.ReportByItemView
import dagger.Module
import dagger.Provides

@Module
class ReportByItemModule(val view: ReportByItemView) {

    @PerActivity
    @Provides
    fun provideView(): ReportByItemView = view
}
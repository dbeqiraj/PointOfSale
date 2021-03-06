package com.dbeqiraj.pointofsale.di.module

import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.vp.view.CartView
import com.dbeqiraj.pointofsale.vp.view.HomeView
import dagger.Module
import dagger.Provides

@Module
class HomeModule(val view: HomeView) {

    @PerActivity
    @Provides
    fun provideView(): CartView = view
}
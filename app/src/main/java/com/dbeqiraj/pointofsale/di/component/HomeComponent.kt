package com.dbeqiraj.pointofsale.di.component

import com.dbeqiraj.pointofsale.di.module.HomeModule
import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.ui.home.HomeActivity
import dagger.Component

@PerActivity
@Component(modules = arrayOf(HomeModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface HomeComponent {
    fun inject(activity: HomeActivity)
}
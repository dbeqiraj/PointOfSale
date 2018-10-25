package com.dbeqiraj.pointofsale.di.component

import com.dbeqiraj.pointofsale.di.module.AddItemModule
import com.dbeqiraj.pointofsale.di.module.CartModule
import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.ui.add_item.AddItemActivity
import com.dbeqiraj.pointofsale.ui.cart.CartActivity
import dagger.Component

@PerActivity
@Component(modules = arrayOf(AddItemModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface AddItemComponent {
    fun inject(activity: AddItemActivity)
}
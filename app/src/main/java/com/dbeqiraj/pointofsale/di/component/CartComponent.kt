package com.dbeqiraj.pointofsale.di.component

import com.dbeqiraj.pointofsale.di.module.CartModule
import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.ui.cart.CartActivity
import com.dbeqiraj.pointofsale.ui.cart.fragment.PickItemsFragment
import dagger.Component

@PerActivity
@Component(modules = arrayOf(CartModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface CartComponent {
    fun inject(activity: CartActivity)
    fun injectFragment(fragment: PickItemsFragment)
}
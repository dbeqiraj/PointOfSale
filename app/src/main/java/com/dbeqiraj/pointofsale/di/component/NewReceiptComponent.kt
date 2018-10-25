package com.dbeqiraj.pointofsale.di.component

import com.dbeqiraj.pointofsale.di.module.NewReceiptModule
import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.ui.new_receipt.NewReceiptActivity
import dagger.Component

@PerActivity
@Component(modules = arrayOf(NewReceiptModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface NewReceiptComponent {
    fun inject(activity: NewReceiptActivity)
}
package com.dbeqiraj.pointofsale.di.component

import com.dbeqiraj.pointofsale.di.module.AddCategoryModule
import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.ui.add_category.AddCategoryActivity
import dagger.Component

@PerActivity
@Component(modules = arrayOf(AddCategoryModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface AddCategoryComponent {
    fun inject(activity: AddCategoryActivity)
}
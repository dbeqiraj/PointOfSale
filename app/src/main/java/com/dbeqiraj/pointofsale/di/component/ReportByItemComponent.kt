package com.dbeqiraj.pointofsale.di.component

import com.dbeqiraj.pointofsale.di.module.ReportByItemModule
import com.dbeqiraj.pointofsale.di.scope.PerActivity
import com.dbeqiraj.pointofsale.ui.report_by_item.ReportByItemActivity
import dagger.Component

@PerActivity
@Component(modules = arrayOf(ReportByItemModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface ReportByItemComponent {
    fun inject(activity: ReportByItemActivity)
}
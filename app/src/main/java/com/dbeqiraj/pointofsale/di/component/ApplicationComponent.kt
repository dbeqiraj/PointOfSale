package com.dbeqiraj.pointofsale.di.component

import android.content.Context
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun exposeContext(): Context
    fun exposeDatabase(): PosDatabase
}
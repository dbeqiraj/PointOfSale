package com.dbeqiraj.pointofsale.application

import android.app.Application
import com.dbeqiraj.pointofsale.di.component.ApplicationComponent
import com.dbeqiraj.pointofsale.di.component.DaggerApplicationComponent
import com.dbeqiraj.pointofsale.di.module.ApplicationModule
import com.squareup.leakcanary.LeakCanary

class PosApp : Application() {

    val mApplicationComponent: ApplicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()
        initializeLeakcanary()
    }

    fun initializeLeakcanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}
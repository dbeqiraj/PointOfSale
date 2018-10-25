package com.dbeqiraj.pointofsale.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dbeqiraj.pointofsale.application.PosApp
import com.dbeqiraj.pointofsale.di.component.ApplicationComponent
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    var menu: Int? = null

    lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setLocale("sq")
        setContentView(getContentView())
        onViewReady(savedInstanceState, intent)
    }

    @CallSuper
    protected open fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        resolveDaggerDependency()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected open fun resolveDaggerDependency() {}

    protected abstract fun getContentView(): Int


    protected fun showBackArrow() {
        val supportActionBar: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setLocale(locale: String) {
        val config = baseContext.resources.configuration
        config.locale = Locale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    protected fun getApplicationComponent(): ApplicationComponent = (getApplication() as PosApp).mApplicationComponent

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this@BaseActivity.menu?.let { menuInflater.inflate(it, menu) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
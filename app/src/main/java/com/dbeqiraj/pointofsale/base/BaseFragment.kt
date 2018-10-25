package com.dbeqiraj.pointofsale.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbeqiraj.pointofsale.application.PosApp
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.di.component.ApplicationComponent
import com.dbeqiraj.pointofsale.ui.cart.CartActivity

abstract class BaseFragment: Fragment() {

    lateinit var receipt: Receipt
    var categoryId: Long = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        categoryId = arguments!!.getLong("id")
        receipt = activity!!.intent.getSerializableExtra("receipt") as Receipt
        return inflater.inflate(getContentView(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }

    protected abstract fun getContentView(): Int

    @CallSuper
    protected open fun onViewReady(savedInstanceState: Bundle?) {
        resolveDaggerDependency()
        setOnClickListeners()
    }

    protected fun getApplicationComponent(): ApplicationComponent = ((context as CartActivity).applicationContext as PosApp).mApplicationComponent

    protected open fun setOnClickListeners() {}

    protected open fun resolveDaggerDependency() {}
}
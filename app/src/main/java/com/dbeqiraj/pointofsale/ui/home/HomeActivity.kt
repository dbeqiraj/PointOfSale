
package com.dbeqiraj.pointofsale.ui.home

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.di.component.DaggerHomeComponent
import com.dbeqiraj.pointofsale.di.module.HomeModule
import com.dbeqiraj.pointofsale.ui.config.ConfigActivity
import com.dbeqiraj.pointofsale.ui.home.adapter.ReceiptsAdapter
import com.dbeqiraj.pointofsale.ui.new_receipt.NewReceiptActivity
import com.dbeqiraj.pointofsale.ui.new_receipt.interfaces.OnReceiptClicked
import com.dbeqiraj.pointofsale.utilities.Constants.REQUEST_CODE_NEW_RECEIPT
import com.dbeqiraj.pointofsale.utilities.Constants.RESULT_CODE_FAILED
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptPresenter
import com.dbeqiraj.pointofsale.vp.view.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_menu_header.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeView, OnReceiptClicked {

    @Inject
    internal lateinit var receiptPresenter: ReceiptPresenter

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        setSupportActionBar(toolbar)
        setupNavDrawer()
        setOnClickListeners()
        title = getString(R.string.receipts)
        getReceipts()
    }

    fun getReceipts(){
        val adapter = ReceiptsAdapter(LayoutInflater.from(this), this)
        setupAdapter(adapter)

        val receiptsLiveData = receiptPresenter.getUnsavedReceipts()
        receiptsLiveData.observe(this, Observer { receipts ->
            adapter.mItemsList.clear()
            adapter.mItemsList.addAll(receipts!!)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupAdapter(adapter: ReceiptsAdapter) {
        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.setLayoutManager(mLayoutManager)
        recycler_view.setItemAnimator(DefaultItemAnimator())
        recycler_view.setAdapter(adapter)
    }

    fun setOnClickListeners() {
        add_receipt.setOnClickListener {
            startNewReceiptActivity(null)
        }

        settings.setOnClickListener {
            drawer.closeDrawer(Gravity.START)
            startActivity(Intent(this, ConfigActivity::class.java))
        }
    }

    override fun receiptClicked(receipt: Receipt) {
        startNewReceiptActivity(receipt)
    }

    private fun startNewReceiptActivity(receipt: Receipt?){
        val intent = Intent(this, NewReceiptActivity::class.java)
        takeIf { receipt != null }?.apply { intent.putExtra("receipt", receipt) }
        startActivityForResult(intent, REQUEST_CODE_NEW_RECEIPT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == REQUEST_CODE_NEW_RECEIPT && resultCode == RESULT_CODE_FAILED ) {
            onShowToast(getString(R.string.cannot_create_receipt))
        }
    }

    @SuppressLint("InflateParams")
    override fun setupNavDrawer(){
        try {
            val toggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    drawerView.requestFocus()
//                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0)
                }
            }

            drawer.addDrawerListener(toggle)
            toggle.syncState()

            val header = layoutInflater.inflate(R.layout.nav_menu_header, null)

            val adapter = ArrayAdapter(this, R.layout.nav_menu_item, resources.getStringArray(R.array.menu_items))
            menu_items.addHeaderView(header)
            menu_items.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(javaClass.simpleName, "Failed at setupNavDrawer!")
        }
    }

    override fun onShowToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun getContentView(): Int = R.layout.activity_home

    override fun resolveDaggerDependency() {
        DaggerHomeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homeModule(HomeModule(this))
                .build().inject(this)
    }
}

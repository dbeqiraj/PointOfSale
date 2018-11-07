package com.dbeqiraj.pointofsale.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.di.component.DaggerCartComponent
import com.dbeqiraj.pointofsale.di.module.CartModule
import com.dbeqiraj.pointofsale.ui.add_item.AddItemActivity
import com.dbeqiraj.pointofsale.ui.cart.extensions.setupCategories
import com.dbeqiraj.pointofsale.ui.cart.extensions.setupFooter
import com.dbeqiraj.pointofsale.vp.presenter.CategoryPresenter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptPresenter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptRowPresenter
import com.dbeqiraj.pointofsale.vp.view.CartView
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class CartActivity : BaseActivity(), CartView {

    lateinit var receipt: Receipt

    @Inject
    internal lateinit var categoryPresenter: CategoryPresenter

    @Inject
    internal lateinit var receiptPresenter: ReceiptPresenter

    @Inject
    internal lateinit var receiptRowPresenter: ReceiptRowPresenter

    @Inject
    internal lateinit var db: PosDatabase

    init {
        menu = R.menu.cart_menu
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        title = getString(R.string.create_cart)
        receipt = intent.getSerializableExtra("receipt") as Receipt
        setupView()
        setOnClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_done -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        setSupportActionBar(toolbar)
        showBackArrow()
        setupCategories()
        setupFooter()
    }

    private fun setOnClickListeners() {
        add_item_fab.setOnClickListener { startActivity(Intent(this@CartActivity, AddItemActivity::class.java)) }
    }

    override fun getContentView(): Int = R.layout.activity_cart

    override fun resolveDaggerDependency() {
        DaggerCartComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cartModule(CartModule(this))
                .build().inject(this)
    }
}

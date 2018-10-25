package com.dbeqiraj.pointofsale.ui.cart.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseFragment
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.helper.PosDatabaseHelper.Companion.DEFAULT_CATEGORY_ID
import com.dbeqiraj.pointofsale.di.component.DaggerCartComponent
import com.dbeqiraj.pointofsale.di.module.CartModule
import com.dbeqiraj.pointofsale.ui.cart.adapter.ItemAdapter
import com.dbeqiraj.pointofsale.ui.cart.extensions.onAddOrRemoveItem
import com.dbeqiraj.pointofsale.vp.presenter.ItemPresenter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptPresenter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptRowPresenter
import com.dbeqiraj.pointofsale.vp.view.PickItemsView
import kotlinx.android.synthetic.main.pick_items_fragment.*
import javax.inject.Inject

class PickItemsFragment : BaseFragment(), PickItemsView {

    var isListPopulated: Boolean = false

    @Inject
    protected lateinit var itemPresenter: ItemPresenter

    @Inject
    internal lateinit var receiptPresenter: ReceiptPresenter

    @Inject
    internal lateinit var receiptRowPresenter: ReceiptRowPresenter

    @Inject
    internal lateinit var db: PosDatabase

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        // Load data only for the default category
        takeIf { categoryId == DEFAULT_CATEGORY_ID.toLong() && !isListPopulated }?.apply { loadData() }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        takeIf { this.isVisible && isVisibleToUser && !isListPopulated }?.apply { loadData() }
    }

    private fun loadData() {
        getItems()
        isListPopulated = true
    }

    private fun getItems(){
        val adapter = ItemAdapter(LayoutInflater.from(activity), onAddOrRemoveItem)
        setupAdapter(adapter)
        val itemsAndReceiptRowLiveData =
                if (categoryId == DEFAULT_CATEGORY_ID.toLong()) {
                    itemPresenter.getItemsAndRowsByReceipt(receipt.id)
                } else {
                    itemPresenter.getItemsAndRowsByReceiptAndCategory(receipt.id, categoryId)
                }

        itemsAndReceiptRowLiveData.observe(this, Observer { itemsAndReceiptRows ->
            adapter.mItemsAndReceiptRowsList.clear()
            adapter.mItemsAndReceiptRowsList.addAll(itemsAndReceiptRows!!)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupAdapter(adapter: ItemAdapter) {
        val mLayoutManager = LinearLayoutManager(context)
        recycler_view.setLayoutManager(mLayoutManager)
        recycler_view.setItemAnimator(DefaultItemAnimator())
        recycler_view.setAdapter(adapter)
    }

    override fun getContentView(): Int = R.layout.pick_items_fragment

    override fun resolveDaggerDependency() {
        DaggerCartComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cartModule(CartModule(this))
                .build().injectFragment(this)
    }
}
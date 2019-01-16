package com.dbeqiraj.pointofsale.ui.cart.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow
import com.dbeqiraj.pointofsale.ui.cart.CartActivity
import com.dbeqiraj.pointofsale.ui.cart.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.footer.*

internal fun CartActivity.setupFooter() {
    val currentSale: LiveData<Receipt> = receiptPresenter.getCurrentSale(receipt.id)
    currentSale.observe(this, Observer { receipt ->
        total_price.text = String.format(getString(R.string.total_money), receipt!!.total.toString())
    })
    val receiptRowsLiveData: LiveData<MutableList<ReceiptRow>> = receiptRowPresenter.getRowsByReceipt(receipt.id)
    receiptRowsLiveData.observe(this, Observer { receiptRows ->
        items_count.text = String.format(getString(R.string.items_count), receiptRows!!.size.toString())
    })
}

internal fun CartActivity.setupCategories() {
    val categoriesLiveData: LiveData<MutableList<Category>> = categoryPresenter.getCategories()

    val adapter = FragmentAdapter(supportFragmentManager)
    view_pager.adapter = adapter
    title_page_indicator.setViewPager(view_pager)

    categoriesLiveData.observe(this, Observer { categories ->
        removeFragments(this)
        adapter.clearCategories()
        adapter.setCategories(categories!!)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = categories.size
    })
}

private fun removeFragments(activity: AppCompatActivity) {
    val fragments = activity.supportFragmentManager.fragments
    if (fragments.size > 0) {
        val ft = activity.supportFragmentManager.beginTransaction()
        for (f in fragments) {
            if (f != null)
                ft.remove(f).commitNowAllowingStateLoss()
        }
    }
}

package com.dbeqiraj.pointofsale.ui.cart.dialog

import android.arch.lifecycle.*
import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.view.View
import android.widget.CompoundButton
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseDialog
import com.dbeqiraj.pointofsale.customized_classes.CustomTextWatcher
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow
import com.dbeqiraj.pointofsale.ui.cart.CartActivity
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptRowPresenter
import kotlinx.android.synthetic.main.dialog_edit_item.*
import org.jetbrains.anko.doAsync

class EditItemDialog(context: Context, itemAndReceiptRow: ItemAndReceiptRow) : BaseDialog(context) {

    private var mItemAndReceiptRow: ItemAndReceiptRow = itemAndReceiptRow

    private val db: PosDatabase = (context as CartActivity).db

    private val receiptRowPresenter: ReceiptRowPresenter = (context as CartActivity).receiptRowPresenter

    private lateinit var receiptRowLiveData: LiveData<ReceiptRow>

    init {
        onViewReady()
    }

    override fun onViewReady() {
        if (mItemAndReceiptRow.isReceiptRowInitialized()) {
            dialog.amount.setText(NumberUtils.formatNumber(mItemAndReceiptRow.receiptRow.amount))
            dialog.price.setText(NumberUtils.formatNumber(mItemAndReceiptRow.receiptRow.unitPrice))
            setTotal()
            dialog.apply_tax.isChecked = mItemAndReceiptRow.receiptRow.hasTax
        } else {
            dialog.amount.setText("0")
            dialog.price.setText(NumberUtils.formatNumber(mItemAndReceiptRow.item.price))
            dialog.total.setText("0")
        }
        setListeners()
    }

    private fun setListeners(){
        dialog.amount.addTextChangedListener(onAmountChanged)
        dialog.price.addTextChangedListener(onPriceChanged)
        dialog.done.setOnClickListener(onDone())
        dialog.apply_tax.setOnCheckedChangeListener(onCheckedChangeListener())
        dialog.setOnDismissListener(onDialogDismiss)
    }

    private fun setTotal() {
        receiptRowLiveData = receiptRowPresenter.getRowById(mItemAndReceiptRow.receiptRow.id)
        receiptRowLiveData.observe((mContext as CartActivity), Observer { receiptRow ->
            dialog.total.setText(NumberUtils.formatNumber(receiptRow!!.totalPrice))
        })
    }

    private val onDialogDismiss
        get() = object : DialogInterface.OnDismissListener {
            override fun onDismiss(p0: DialogInterface?) {
                receiptRowLiveData.removeObservers((mContext as CartActivity))
            }
        }

    private val onAmountChanged
        get() = object : CustomTextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var amount = dialog.amount.text.toString().toFloatOrNull()
                takeIf { amount == null }?.apply { amount = 0F }
                mItemAndReceiptRow.receiptRow.amount = amount as Float
                doUpdate()
            }
        }

    private val onPriceChanged
        get() = object : CustomTextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var price = dialog.price.text.toString().toFloatOrNull()
                takeIf { price == null }?.apply { price = 0F }
                mItemAndReceiptRow.receiptRow.unitPrice = price as Float
                doUpdate()
            }
        }

    private fun onDone() = View.OnClickListener {
        dialog.dismiss()
    }

    private fun onCheckedChangeListener() = CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton, checked: Boolean ->
        mItemAndReceiptRow.receiptRow.hasTax = checked
        doUpdate()
    }

    private fun doUpdate() {
        doAsync {
            db.receiptRowDao().update(mItemAndReceiptRow.receiptRow)
            db.receiptRowDao().updateTotal(mItemAndReceiptRow.receiptRow.id)
            db.receiptDao().updateTotal(mItemAndReceiptRow.receiptRow.receiptId)
        }
    }

    override fun getContentView(): Int = R.layout.dialog_edit_item

}
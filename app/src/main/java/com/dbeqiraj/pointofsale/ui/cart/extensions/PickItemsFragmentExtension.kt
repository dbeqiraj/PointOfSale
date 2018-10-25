package com.dbeqiraj.pointofsale.ui.cart.extensions

import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow
import com.dbeqiraj.pointofsale.ui.cart.fragment.PickItemsFragment
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnAddOrRemoveItem
import org.jetbrains.anko.doAsync

val PickItemsFragment.onAddOrRemoveItem: OnAddOrRemoveItem
    get() = object : OnAddOrRemoveItem {
        override fun onAdd(itemAndReceiptRow: ItemAndReceiptRow) {
            doAsync {
                val receiptRow = receiptRowPresenter.getRowByReceiptAndItem(receipt.id, itemAndReceiptRow.item.id)
                if (receiptRow != null) {
                    receiptRow.amount++
                    db.receiptRowDao().update(receiptRow)
                } else {
                    val newRow = ReceiptRow(1F, itemAndReceiptRow.item.price, itemAndReceiptRow.item.price, itemAndReceiptRow.item.tax!!, true, itemAndReceiptRow.item.id, receipt.id)
                    db.receiptRowDao().insert(newRow)
                }
                db.receiptDao().updateTotal(receipt.id)
            }
        }

        override fun onRemove(itemAndReceiptRow: ItemAndReceiptRow) {
            doAsync {
                val receiptRow = receiptRowPresenter.getRowByReceiptAndItem(receipt.id, itemAndReceiptRow.item.id)
                if (receiptRow != null) {
                    if ( receiptRow.amount > 1 ) {
                        receiptRow.amount--
                        db.receiptRowDao().update(receiptRow)
                    } else {
                        db.receiptRowDao().delete(receiptRow)
                    }
                }
                db.receiptDao().updateTotal(receipt.id)
            }
        }
    }
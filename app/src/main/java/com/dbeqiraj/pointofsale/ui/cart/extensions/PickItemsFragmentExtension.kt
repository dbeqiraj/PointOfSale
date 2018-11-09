package com.dbeqiraj.pointofsale.ui.cart.extensions

import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow
import com.dbeqiraj.pointofsale.ui.cart.dialog.EditItemDialog
import com.dbeqiraj.pointofsale.ui.cart.fragment.PickItemsFragment
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnAddOrRemoveItem
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnItemViewClick
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

internal val PickItemsFragment.onAddOrRemoveItem: OnAddOrRemoveItem
    get() = object : OnAddOrRemoveItem {
        override fun onAdd(itemAndReceiptRow: ItemAndReceiptRow) {
            doAsync {
                val receiptRow = receiptRowPresenter.getRowByReceiptAndItem(receipt.id, itemAndReceiptRow.item.id)
                if (receiptRow != null) {
                    receiptRow.amount++
                    db.receiptRowDao().update(receiptRow)
                    db.receiptRowDao().updateTotal(receiptRow.id)
                } else {
                    insertReceiptRow(itemAndReceiptRow, 1F)
                }
                db.receiptDao().updateTotal(receipt.id)
            }
        }

        override fun onRemove(itemAndReceiptRow: ItemAndReceiptRow) {
            doAsync {
                val receiptRow = receiptRowPresenter.getRowByReceiptAndItem(receipt.id, itemAndReceiptRow.item.id)
                if (receiptRow != null) {
                    if (receiptRow.amount > 1) {
                        receiptRow.amount--
                        db.receiptRowDao().update(receiptRow)
                        db.receiptRowDao().updateTotal(receiptRow.id)
                    } else {
                        db.receiptRowDao().delete(receiptRow)
                    }
                }
                db.receiptDao().updateTotal(receipt.id)
            }
        }
    }

internal val PickItemsFragment.onItemViewClick: OnItemViewClick
    get() = object : OnItemViewClick {
        override fun onClick(itemAndReceiptRow: ItemAndReceiptRow) {
            doAsync {
                createNewReceiptRowIfNull(itemAndReceiptRow)
                uiThread { EditItemDialog(context!!, itemAndReceiptRow) }
            }
        }
    }

private fun PickItemsFragment.createNewReceiptRowIfNull(itemAndReceiptRow: ItemAndReceiptRow) {
    if (!itemAndReceiptRow.isReceiptRowInitialized()) {
        val newRow = insertReceiptRow(itemAndReceiptRow, 0F)
        itemAndReceiptRow.receiptRow = newRow
    }
}

private fun PickItemsFragment.insertReceiptRow(itemAndReceiptRow: ItemAndReceiptRow, amount: Float): ReceiptRow {
    val row_total_price = if (amount > 0) itemAndReceiptRow.item.price * (amount + itemAndReceiptRow.item.tax!! / 100) else 0F
    val newRow = ReceiptRow(amount, itemAndReceiptRow.item.price, row_total_price, itemAndReceiptRow.item.tax!!, true, itemAndReceiptRow.item.id, receipt.id)
    val id = db.receiptRowDao().insert(newRow)
    newRow.id = id
    return newRow
}


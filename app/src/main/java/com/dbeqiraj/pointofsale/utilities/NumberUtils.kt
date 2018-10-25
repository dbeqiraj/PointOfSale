package com.dbeqiraj.pointofsale.utilities

import java.text.DecimalFormat

object NumberUtils {
    fun formatNumber(number: Float): String {
        val formatter = DecimalFormat("#,###,###")
        formatter.minimumFractionDigits = 2
        return formatter.format(number)
    }

    fun formatAmount(amount: Float): String {
        return if (amount == amount.toInt().toFloat())
            amount.toInt().toString()
        else
            String.format("%.02f", amount)
    }
}
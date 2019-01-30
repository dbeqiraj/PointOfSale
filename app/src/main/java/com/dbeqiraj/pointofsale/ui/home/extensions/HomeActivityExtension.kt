package com.dbeqiraj.pointofsale.ui.home.extensions

import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import com.dbeqiraj.pointofsale.ui.home.HomeActivity
import com.dbeqiraj.pointofsale.ui.report_by_item.ReportByItemActivity
import com.dbeqiraj.pointofsale.utilities.DrawerReportsIndexes.REPORT_BY_ITEM
import kotlinx.android.synthetic.main.activity_home.*

internal val HomeActivity.onMenuItemClicked: AdapterView.OnItemClickListener
    get() = object : AdapterView.OnItemClickListener {
        override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
            when(position) {
                REPORT_BY_ITEM + 1 -> {
                    startActivity(Intent(baseContext, ReportByItemActivity::class.java))
                }
            }
            drawer.closeDrawer(Gravity.START)
        }
    }
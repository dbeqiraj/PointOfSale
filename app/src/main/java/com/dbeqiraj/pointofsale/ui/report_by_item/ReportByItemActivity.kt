package com.dbeqiraj.pointofsale.ui.report_by_item

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.di.component.DaggerReportByItemComponent
import com.dbeqiraj.pointofsale.di.module.ReportByItemModule
import com.dbeqiraj.pointofsale.ui.report_by_item.extensions.changeVisibility
import com.dbeqiraj.pointofsale.utilities.DrawerReportsIndexes.REPORT_BY_ITEM
import com.dbeqiraj.pointofsale.vp.view.ReportByItemView
import kotlinx.android.synthetic.main.content_report_by_item.*
import kotlinx.android.synthetic.main.toolbar.*

class ReportByItemActivity : BaseActivity(), ReportByItemView {

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        title = resources.getStringArray(R.array.menu_items)[REPORT_BY_ITEM]
        setupContentView()
        setListeners()
    }

    private fun setupContentView() {
        setSupportActionBar(toolbar)
        showBackArrow()
    }

    override fun setListeners() {
        today_only.setOnCheckedChangeListener({ button: CompoundButton, checked: Boolean ->
            changeVisibility(checked, pick_date_container)
        })
    }

    override fun getContentView(): Int = R.layout.activity_report_by_item

    override fun resolveDaggerDependency() {
        DaggerReportByItemComponent.builder()
                .applicationComponent(getApplicationComponent())
                .reportByItemModule(ReportByItemModule(this))
                .build().inject(this)
    }
}

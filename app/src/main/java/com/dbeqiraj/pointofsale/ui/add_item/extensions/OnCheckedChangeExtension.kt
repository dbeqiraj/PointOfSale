package com.dbeqiraj.pointofsale.ui.add_item.extensions

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.preference.PreferenceManager
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Switch
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.ui.add_item.AddItemActivity
import kotlinx.android.synthetic.main.content_add_item.*

internal fun AddItemActivity.setupTaxSection() {
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    vat.setText(prefs.getString(getString(R.string.pref_tax), getString(R.string.pref_default_vat)))

    setOnCheckedChangeListener(vat_switch, vat)
}

private fun setOnCheckedChangeListener(vat_switch: Switch, vat: EditText) {
    vat_switch.setOnCheckedChangeListener({ compoundButton: CompoundButton, isChecked: Boolean ->
        vat.isEnabled = isChecked
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            vat_switch.getThumbDrawable().setColorFilter(if (isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
        }
    })
}
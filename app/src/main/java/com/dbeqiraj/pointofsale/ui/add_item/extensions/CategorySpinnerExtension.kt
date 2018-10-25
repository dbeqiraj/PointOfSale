package com.dbeqiraj.pointofsale.ui.add_item.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.ui.add_category.AddCategoryActivity
import com.dbeqiraj.pointofsale.ui.add_item.AddItemActivity
import kotlinx.android.synthetic.main.content_add_item.*

internal fun AddItemActivity.setupCategorySpinner() {
    val categoriesList: MutableList<String> = ArrayList()
    val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.spinner_onselected, categoriesList)
    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
    category.adapter = adapter
    fillAdapter(categoriesList)
    onItemSelectedListener()
}

private fun AddItemActivity.fillAdapter(categoriesList: MutableList<String>) {
    var firstLoad = true
    categoriesLiveData = categoryPresenter.getCategories()
    categoriesLiveData.observe(this, Observer { categories ->
        // Remove default category (All Items) from the list
        categories!!.removeAt(0)
        categoriesList.clear()
        categoriesList.addAll(categories.map { it.name })
        categoriesList.add(0, getString(R.string.select))
        categoriesList.add(categoriesList.size, getString(R.string.add_category))
        (category.adapter as ArrayAdapter<*>).notifyDataSetChanged()
        if (firstLoad) {
            category.setSelection(0)
            firstLoad = false
        } else {
            setSelectionToLastAdded(categoriesList, categories)
        }
    })
}

private fun AddItemActivity.setSelectionToLastAdded(categoriesList: MutableList<String>, categories: MutableList<Category>) {
    //Find the last added category
    val lastAddedCategory = categories.maxBy { it.id }
    category.setSelection(categoriesList.indexOf(lastAddedCategory!!.name))
}

private fun AddItemActivity.onItemSelectedListener() {
    category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position != 0 && position == category.count - 1) {
                startActivity(Intent(this@onItemSelectedListener, AddCategoryActivity::class.java))
                // Set selection to 'Select'
                category.setSelection(0)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}
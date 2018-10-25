package com.dbeqiraj.pointofsale.ui.cart.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.ui.cart.fragment.PickItemsFragment
import java.util.ArrayList

class FragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mCategoriesList = ArrayList<Category>()

    override fun getItem(position: Int): Fragment {
        val fragment = PickItemsFragment()
        val bundle = Bundle()
        bundle.putLong("id", mCategoriesList[position].id)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int = mCategoriesList.size

    fun clearCategories() = mCategoriesList.clear()

    fun setCategories(categories: MutableList<Category>) = mCategoriesList.addAll(categories)

    override fun getPageTitle(position: Int): CharSequence? = mCategoriesList[position].name
}
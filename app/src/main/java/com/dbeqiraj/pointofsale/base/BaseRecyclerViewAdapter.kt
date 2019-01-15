package com.dbeqiraj.pointofsale.base

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

abstract class BaseRecyclerViewAdapter constructor(layoutInflater: LayoutInflater) : RecyclerView.Adapter<BaseViewHolder>() {

    private val FOOTER_VIEW = 1

    internal lateinit var mContext: Context
    internal lateinit var prefs: SharedPreferences
    private val mLayoutInflater: LayoutInflater = layoutInflater

    val mItemsList: MutableList<BaseEntity> = ArrayList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mContext = recyclerView.context
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when(viewType) {
            FOOTER_VIEW -> return BaseRecyclerViewAdapter.FooterViewHolder(mLayoutInflater.inflate(getFooterContentView(), parent, false))
            else -> return BaseRecyclerViewAdapter.ItemViewHolder(mLayoutInflater.inflate(getItemContentView(), parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = fillItemView(holder.view, mItemsList[position])

    override fun getItemCount(): Int = mItemsList.size

    protected abstract fun fillItemView(view: View, item: BaseEntity)

    class ItemViewHolder(view: View) : BaseViewHolder(view)

    class FooterViewHolder(view: View) : BaseViewHolder(view)

    protected abstract fun getItemContentView(): Int

    protected open fun getFooterContentView(): Int = -1

    protected fun getString(id: Int) = mContext.getString(id)

    protected fun getStringSharedPref(key: Int, default: String) = prefs.getString(mContext.getString(key), default)
}
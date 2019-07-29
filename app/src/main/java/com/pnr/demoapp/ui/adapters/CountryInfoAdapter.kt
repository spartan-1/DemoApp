package com.pnr.demoapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.pnr.demoapp.R
import com.pnr.demoapp.model.InfoEntry
import com.pnr.demoapp.ui.viewholders.InfoViewHolder

/**
 * CountryInfoAdapter
 */
class CountryInfoAdapter(private val infoList: ArrayList<InfoEntry>, private val clickListener: (InfoEntry) -> Unit) :
    RecyclerView.Adapter<InfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val layourInflator = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layourInflator, R.layout.list_item_country_info, parent, false)
        return InfoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    override fun onBindViewHolder(infoViewHolder: InfoViewHolder, position: Int) {
        infoViewHolder.bind(infoList[position], clickListener)
    }

    /**
     * function to update adapter data
     */
    fun updateData(infoList: ArrayList<InfoEntry>) {
        this.infoList.clear()
        this.infoList.addAll(infoList)
        notifyDataSetChanged()
    }

    /**
     * function to add data
     */
    fun addData(infoList: ArrayList<InfoEntry>) {
        this.infoList.addAll(infoList)
        notifyDataSetChanged()
    }
}
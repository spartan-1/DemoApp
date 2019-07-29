package com.pnr.demoapp.ui.viewholders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.pnr.demoapp.BR
import com.pnr.demoapp.model.InfoEntry
import kotlinx.android.synthetic.main.list_item_country_info.view.*

/**
 * InfoViewHolder
 */
class InfoViewHolder(private val viewBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(data: Any, clickListener: (InfoEntry) -> Unit) {
        viewBinding.setVariable(BR.countryInfo, data)
        viewBinding.root.image_info.setOnClickListener { clickListener(data as InfoEntry) }
        viewBinding.executePendingBindings()
    }
}
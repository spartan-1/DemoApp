package com.pnr.demoapp.ui.viewholders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.pnr.demoapp.BR

/**
 * InfoViewHolder
 */
class InfoViewHolder(private val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(data: Any) {
        viewBinding.setVariable(BR.countryInfo, data)
        viewBinding.executePendingBindings()
    }
}
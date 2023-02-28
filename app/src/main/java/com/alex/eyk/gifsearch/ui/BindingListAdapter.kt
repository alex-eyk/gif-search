package com.alex.eyk.gifsearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<B : ViewDataBinding, T : Any, VH : BindingViewHolder<T, B>>(
    @LayoutRes private val layoutRes: Int,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<B>(
            inflater,
            layoutRes,
            parent,
            false
        )
        return onCreateViewHolder(binding)
    }

    protected abstract fun onCreateViewHolder(binding: B): VH

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        holder.bindTo(getItem(position))
    }
}

abstract class BindingViewHolder<T : Any, B : ViewDataBinding>(
    protected val binding: B
) : RecyclerView.ViewHolder(
    binding.root
) {

    abstract fun bindTo(item: T)
}

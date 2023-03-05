package com.alex.eyk.gifsearch.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.databinding.ItemSquareGifBinding
import com.alex.eyk.gifsearch.ui.search.adapter.GifAdapter.GifViewHolder

class GifAdapter : PagingDataAdapter<Gif, GifViewHolder>(
    diffCallback = GifsDiffCallback()
) {

    var onItemClick: ((gif: Gif) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSquareGifBinding>(
            inflater,
            R.layout.item_square_gif,
            parent,
            false
        )
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GifViewHolder,
        position: Int
    ) {
        holder.bindTo(getItem(position))
    }

    inner class GifViewHolder(
        private val binding: ItemSquareGifBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Gif?) {
            with(binding) {
                gif = item
                onItemClick = this@GifAdapter.onItemClick
                executePendingBindings()
            }
        }
    }

    class GifsDiffCallback : DiffUtil.ItemCallback<Gif>() {

        override fun areItemsTheSame(old: Gif, new: Gif) = old.id == new.id

        override fun areContentsTheSame(old: Gif, new: Gif) = old == new
    }
}

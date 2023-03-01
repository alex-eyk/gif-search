package com.alex.eyk.gifsearch.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.databinding.ItemGifBinding
import com.alex.eyk.gifsearch.ui.BindingListAdapter
import com.alex.eyk.gifsearch.ui.BindingViewHolder
import com.alex.eyk.gifsearch.ui.search.adapter.GifAdapter.GifViewHolder

class GifAdapter : BindingListAdapter<ItemGifBinding, Gif, GifViewHolder>(
    diffCallback = GifsDiffCallback(),
    layoutRes = R.layout.item_gif
) {

    override fun onCreateViewHolder(
        binding: ItemGifBinding
    ) = GifViewHolder(binding)

    class GifViewHolder(
        binding: ItemGifBinding
    ) : BindingViewHolder<Gif, ItemGifBinding>(binding) {

        override fun bindTo(item: Gif) {
            binding.apply {
                this.gif = item
                executePendingBindings()
            }
        }
    }

    class GifsDiffCallback : DiffUtil.ItemCallback<Gif>() {

        override fun areItemsTheSame(old: Gif, new: Gif) = old.id == new.id

        override fun areContentsTheSame(old: Gif, new: Gif) = old == new
    }
}
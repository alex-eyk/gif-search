package com.alex.eyk.gifsearch.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.alex.eyk.gifsearch.databinding.ItemSuggestionBinding
import com.alex.eyk.gifsearch.ui.BindingListAdapter
import com.alex.eyk.gifsearch.ui.BindingViewHolder
import com.alex.eyk.gifsearch.ui.search.adapter.SuggestionAdapter.SuggestionViewHolder

class SuggestionAdapter :
    BindingListAdapter<ItemSuggestionBinding, Suggestion, SuggestionViewHolder>(
        diffCallback = SuggestionsDiffCallback(),
        layoutRes = R.layout.item_suggestion
    ) {

    var onItemClick: ((suggestion: Suggestion) -> Unit)? = null

    override fun onCreateViewHolder(
        binding: ItemSuggestionBinding
    ) = SuggestionViewHolder(binding)

    inner class SuggestionViewHolder(
        binding: ItemSuggestionBinding
    ) : BindingViewHolder<Suggestion, ItemSuggestionBinding>(binding) {

        override fun bindTo(item: Suggestion) {
            binding.suggestion = item
            binding.onItemClick = onItemClick
            binding.executePendingBindings()
        }
    }

    class SuggestionsDiffCallback : DiffUtil.ItemCallback<Suggestion>() {

        override fun areItemsTheSame(
            old: Suggestion,
            new: Suggestion
        ) = areContentsTheSame(old, new)

        override fun areContentsTheSame(
            old: Suggestion,
            new: Suggestion
        ) = old == new
    }
}

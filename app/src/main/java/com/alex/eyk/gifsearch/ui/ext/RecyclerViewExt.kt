package com.alex.eyk.gifsearch.ui.ext

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrolledToBottomListener(
    onScrolledToBottom: () -> Unit
) {
    this.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    onScrolledToBottom()
                }
            }
        }
    )
}

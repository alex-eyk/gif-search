package com.alex.eyk.gifsearch.ui.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State

class SpacesItemDecoration(
    private val spaceInPx: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    constructor(
        spaceInPx: Number,
        spanCount: Number
    ) : this(
        spaceInPx.toInt(),
        spanCount.toInt()
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: State
    ) {
        if (parent.getChildAdapterPosition(view) % spanCount == 0) {
            outRect.left = spaceInPx
            outRect.right = spaceInPx / 2
        } else {
            outRect.left = spaceInPx / 2
            outRect.right = spaceInPx
        }
        outRect.bottom = spaceInPx
        if (parent.getChildAdapterPosition(view) in 0 until spanCount) {
            outRect.top = spaceInPx
        }
    }
}

package com.example.marvel.core.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * MarginItemDecoration class is used to apply is equal space from all sides of RecyclerView
 */
class MarginItemDecoration(private val spaceSize: Int, private val orientation: String) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (orientation == Constants.KEY_VERTICAL) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceSize
                }
                left = spaceSize
                right = spaceSize
                bottom = spaceSize
            }
        } else if (orientation == Constants.KEY_HORIZONTAL) {

            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = spaceSize
                }
                top = spaceSize
                right = spaceSize
                bottom = spaceSize
            }
        }
    }
}
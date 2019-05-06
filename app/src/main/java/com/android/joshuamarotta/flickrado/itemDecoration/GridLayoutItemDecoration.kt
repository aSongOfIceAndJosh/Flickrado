package com.android.joshuamarotta.flickrado.itemDecoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridLayoutItemDecoration(private val colSpan: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        outRect.top = if (itemPosition < colSpan) 10 else 5
        outRect.bottom = 5

        if (itemPosition % colSpan == 0 ) {
            outRect.left = 10
            outRect.right = 5
        } else if (itemPosition % colSpan == 2 ) {
            outRect.right = 10
            outRect.left = 5
        }
    }
}
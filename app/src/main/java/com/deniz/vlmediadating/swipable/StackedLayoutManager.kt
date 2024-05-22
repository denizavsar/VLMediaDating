package com.deniz.vlmediadating.swipable

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.deniz.vlmediadating.R
import com.deniz.vlmediadating.utils.Constants
import com.google.android.material.card.MaterialCardView

class StackedLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val itemCount = itemCount
        if (itemCount == 0) return

        val min = minOf(itemCount, Constants.MAX_VISIBLE_CARD_COUNT)
        for (i in 0 until min) {
            val view = recycler.getViewForPosition(i)
            view.translationZ =
                (min - i - 1) * Constants.CARD_TRANSLATION_Z_FACTOR // Adjust translationZ to reverse the order
            view.rotation = 0f
            view.alpha = 1f
            view.translationX = 0f
            addView(view, 0)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecoratedWithMargins(
                view,
                i * 25,
                i * 50,
                i * 25 + width,
                i * 50 + height
            )
        }
    }

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}
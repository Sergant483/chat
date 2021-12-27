package com.senla.chat.presentation.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.senla.chat.R

class ChatRecyclerViewDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val margin = view.resources.getDimension(R.dimen.margin_medium).toInt()
        outRect.bottom = margin
    }
}

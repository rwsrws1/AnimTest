package com.example.myapplication.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class CardItemDecoration : RecyclerView.ItemDecoration() {
    private val mPaint = Paint()

    init {
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 40F
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition != 0) {
            outRect.set(80, 80, 80, 80)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(child)
            if (index == 0) continue
            val params = child.layoutParams as RecyclerView.LayoutParams
//            val left = parent.paddingLeft
//            val top = child.bottom + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))
//            val top = child.top
//            val right = parent.width - parent.paddingRight
//            val bottom = top + mDivider
            val left = child.left - mPaint.strokeWidth
            val top = child.top - mPaint.strokeWidth
            val right = child.right + mPaint.strokeWidth
            val bottom = child.bottom + mPaint.strokeWidth
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
        }
    }
}
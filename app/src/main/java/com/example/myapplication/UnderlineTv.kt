package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet

class UnderlineTv(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    private val paint = Paint()

    init {
        paint.color = Color.RED
        paint.strokeWidth = 5F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0F, baseline.toFloat(), width.toFloat(), baseline.toFloat(), paint)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawColor(Color.parseColor("#2f000000"))
    }
}
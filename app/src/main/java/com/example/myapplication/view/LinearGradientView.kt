package com.example.myapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LinearGradientView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private val linearGradient = LinearGradient(100F, 100F, 500F, 500F, Color.RED, Color.BLUE, Shader.TileMode.CLAMP)
    private val sweepGradient = SweepGradient(700F, 700F, Color.RED, Color.BLUE)

    init {
        paint.shader = linearGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        canvas.drawRect(0F, 0F, 1000F, 1000F, paint)
        paint.style = Paint.Style.STROKE
        paint.shader = null
        paint.strokeWidth = 2F
        canvas.drawRect(100F, 100F, 500F, 500F, paint)

        paint.style = Paint.Style.FILL
        paint.shader = sweepGradient
        canvas.drawCircle(700F, 700F, 100F, paint)
    }
}
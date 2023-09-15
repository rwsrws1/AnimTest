package com.example.myapplication.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class TextShaderView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private var offset = 0F
    private lateinit var linearGradient: LinearGradient
    private val colors = intArrayOf(Color.parseColor("#4C4D4B"), Color.parseColor("#D9D6DA"), Color.parseColor("#4C4D4B"))
    private val positions = floatArrayOf(0.2F, 0.5F, 0.8F)

    init {
        paint.textSize = 170F
        val valueAnimator = ValueAnimator()
        valueAnimator.setFloatValues(-1000F, 1000F)
        valueAnimator.duration = 2000
        valueAnimator.repeatCount = -1
        valueAnimator.addUpdateListener {
            offset = it.animatedValue as Float
            linearGradient = LinearGradient(offset, 300F, 1000F + offset, 600F, colors, positions, Shader.TileMode.CLAMP)
            paint.shader = linearGradient
            invalidate()
        }
        valueAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#232423"))
        paint.alpha = 255
        canvas.drawText("演示文字", 200F, 500F, paint)
        canvas.drawRect(0F, 600F, 1000F, 1000F, paint)
    }
}
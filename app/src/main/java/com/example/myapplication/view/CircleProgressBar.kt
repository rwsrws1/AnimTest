package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R

class CircleProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress = 0
    private var progressColor = Color.BLUE
    private var backgroundColor = Color.GRAY
    private var strokeWidth = 10f

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val rectF = RectF()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgressBar,
            0, 0
        ).apply {
            try {
                progress = getInteger(R.styleable.CircleProgressBar_progress, 0)
                progressColor = getColor(R.styleable.CircleProgressBar_progressColor, Color.BLUE)
                backgroundColor = getColor(R.styleable.CircleProgressBar_backgroundColor, Color.GRAY)
                strokeWidth = getDimension(R.styleable.CircleProgressBar_strokeWidth, 10f)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width - strokeWidth) / 2f

        // Draw background circle
        paint.color = backgroundColor
        paint.strokeWidth = strokeWidth
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Draw progress arc
        paint.color = progressColor
        rectF.set(strokeWidth / 2f, strokeWidth / 2f, width - strokeWidth / 2f, height - strokeWidth / 2f)
        canvas.drawArc(rectF, -90f, progress * 3.6f, false, paint)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun setProgressColor(color: Int) {
        progressColor = color
        invalidate()
    }

    override fun setBackgroundColor(color: Int) {
        backgroundColor = color
        invalidate()
    }

    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        invalidate()
    }
}

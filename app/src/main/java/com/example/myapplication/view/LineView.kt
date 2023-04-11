package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class LineView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private val defaultPath: Path = Path()
    private val defaultPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    init {
        defaultPaint.style = Paint.Style.STROKE
            defaultPaint.strokeWidth = 10f
            defaultPaint.color = Color.RED
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(defaultPath, defaultPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> defaultPath.moveTo(x,y)
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> defaultPath.lineTo(x,y)
        }
        invalidate()
        return true
    }

    fun clear() {
        defaultPath.reset()
        invalidate()
    }
}
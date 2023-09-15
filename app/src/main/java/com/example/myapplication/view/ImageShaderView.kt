package com.example.myapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.example.myapplication.R
import kotlin.math.abs

class ImageShaderView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private val path = Path()
    private var mX = 0f
    private var mY = 0f
    private val offset = ViewConfiguration.get(context).scaledTouchSlop
    private lateinit var bitmap: Bitmap

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 100f
        paint.strokeCap = Paint.Cap.ROUND
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.shader)
        val shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        paint.shader = shader
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                val x = event.x
                val y = event.y
                mX = x
                mY = y
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                val x1 = event.x
                val y2 = event.y
                val preX = mX
                val preY = mY
                val dx = abs(x1 - preX)
                val dy = abs(y2 - preY)
                if (dx >= offset || dy >= offset) {
                    val cX = (x1 + preX) / 2
                    val cY = (y2 + preY) / 2
                    path.quadTo(preX, preY, cX, cY)
                    mX = x1
                    mY = y2
                }
            }
        }
        invalidate()
        return true
    }
}
package com.hcn.auto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View

class CircleProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mCircleColor = Color.argb(0x80, 0x92, 0x94, 0x95)
    private var mRIngColor = Color.argb(0xff, 0xff, 0xe0, 0x16)
    private var mRadius = 103F
    private var mRingRadius = 103F
    private var mStrokeWidth = 3F
    private var mProgress = 0F
    private var mTotalProgress = 100F
    private val mCirclePaint = Paint()
    private val mRingPaint = Paint()

    companion object {
        private val TAG = CircleProgressView::class.java.simpleName
    }

    init {
        Log.d(TAG, "CircleProgressView init")
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        mCircleColor = typedArray.getColor(R.styleable.CircleProgressView_circle_color, mCircleColor)
        mRIngColor = typedArray.getColor(R.styleable.CircleProgressView_ring_color, mRIngColor)
        mRadius = typedArray.getDimension(R.styleable.CircleProgressView_radius, mRadius)
        mRingRadius = typedArray.getDimension(R.styleable.CircleProgressView_ring_radius, mRingRadius)
        mStrokeWidth = typedArray.getDimension(R.styleable.CircleProgressView_stroke_width, mStrokeWidth)
        typedArray.recycle()

        mCirclePaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = mCircleColor
            strokeWidth = mStrokeWidth
        }

        mRingPaint.apply {
            isAntiAlias = true
            color = mRIngColor
            style = Paint.Style.STROKE
            strokeWidth = mStrokeWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        val mXCenter = width/2F
        val mYCenter = height/2F
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint)

        if (mProgress > 0) {
            val oval = RectF()
            oval.apply {
                left = mXCenter - mRingRadius
                top = mYCenter - mRingRadius
                right = mRingRadius * 2 + left
                bottom = mRingRadius * 2 + top
                canvas.drawArc(oval, -90F, (mProgress/mTotalProgress) * 360F, false, mRingPaint)
            }
        }
    }

    private fun sp2px(sp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics)
    }

    private fun dip2px(dip: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics)
    }

    fun setProgress(progress: Int) {
        mProgress = progress.toFloat()
        postInvalidate()
    }
}
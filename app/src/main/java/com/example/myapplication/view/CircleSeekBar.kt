package com.example.myapplication.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.myapplication.R

class CircleSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Define the progress value
    private var progress = 0f

    // Define the paint for the progress bar
    private val progressPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    // Define the paint for the background of the progress bar
    private val backgroundPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
    }

    // Define the animation for the progress bar
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            progress = it.animatedValue as Float
            invalidate()
        }
    }

    init {
        // Get the custom attributes from the layout file
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomProgressBar,
            defStyleAttr,
            0
        ).apply {
            try {
                // Set the progress bar color from the custom attribute
                progressPaint.color = getColor(
                    R.styleable.CustomProgressBar_seekProgressColor,
                    Color.BLUE
                )
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the background of the progress bar
        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            backgroundPaint
        )

        // Draw the progress bar
        canvas.drawRect(
            0f,
            0f,
            width.toFloat() * progress,
            height.toFloat(),
            progressPaint
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Start the animation when the user touches the progress bar
                animator.start()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}

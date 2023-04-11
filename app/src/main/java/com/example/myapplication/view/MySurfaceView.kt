package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception

class MySurfaceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback, Runnable{
    private lateinit var  mSurfaceHolder: SurfaceHolder
    private lateinit var mCanvas: Canvas
    private var startDraw = false
    private val mPath = Path()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    init {
        initView()
    }

    companion object {
        private val TAG = MySurfaceView::class.java.simpleName
    }

    private fun initView() {
        mSurfaceHolder = holder
        mSurfaceHolder.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        startDraw = true
        Thread(this).start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceDestroyed")
        startDraw = false
    }

    private fun draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas()
            mCanvas.drawColor(Color.WHITE)
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = 5f
            mPaint.color = Color.BLACK
            mCanvas.drawPath(mPath, mPaint)
        } catch (e : Exception) {
        } finally {
            if (startDraw) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mPath.moveTo(x,y)
            MotionEvent.ACTION_MOVE -> mPath.lineTo(x,y)
        }
        return true
    }

    fun reset() {
        mPath.reset()
    }

    override fun run() {
        Log.d(TAG, "run")
        while (startDraw) {
            Log.d(TAG, "draw")
            draw()
        }
    }
}


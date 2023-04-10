package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View

class MiddleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var innerBackground = Color.RED
    private var outerBackground = Color.GREEN
    private var progressTextColor = Color.GREEN
    private var roundWidth = 100
    private var progressTextSize = 100

    private val innerPaint = Paint()
    private val outerPaint = Paint()
    private val textPaint = Paint()

    private var maxProgress: Float = 0F
    private var progress: Float = 0F

    private val rectF = RectF()
    private val textBounds = Rect()

    companion object {
        private val TAG = MiddleView::class.simpleName
    }

    init {
        Log.d(TAG, "init")
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar)
        innerBackground =
            typedArray.getColor(R.styleable.ProgressBar_innerBackground, innerBackground)
        outerBackground =
            typedArray.getColor(R.styleable.ProgressBar_outerBackground, outerBackground)
        progressTextColor =
            typedArray.getColor(R.styleable.ProgressBar_progressTextColor, progressTextColor)
        roundWidth =
            typedArray.getDimension(R.styleable.ProgressBar_roundWidth, dip2px(roundWidth))
                .toInt()
        progressTextSize = typedArray.getDimension(
            R.styleable.ProgressBar_progressTextSize,
            sp2px(progressTextSize)
        ).toInt()
        initData()
        typedArray.recycle()
    }

//    constructor(context: Context?) : this(context, null, 0) {
//        Log.d(TAG, "constructor 1")
//    }
//
//    constructor(context: Context?, attributeSet: AttributeSet) : this(context, attributeSet, 0) {
//        Log.d(TAG, "constructor 2")
//    }

    private fun initData() {
        innerPaint.isAntiAlias = true //抗锯齿
        innerPaint.color = innerBackground
        innerPaint.strokeWidth = roundWidth.toFloat() //线条宽度
        innerPaint.style = Paint.Style.STROKE //空心

        outerPaint.isAntiAlias = true //抗锯齿
        outerPaint.color = outerBackground
        outerPaint.strokeWidth = roundWidth.toFloat() //线条宽度
        outerPaint.style = Paint.Style.STROKE //空心

        textPaint.isAntiAlias = true //抗锯齿
        textPaint.color = progressTextColor
        textPaint.strokeWidth = roundWidth.toFloat()
        textPaint.textSize = progressTextSize.toFloat()
    }

    private fun sp2px(sp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics)
    }

    private fun dip2px(dip: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = MeasureSpec.getSize(heightMeasureSpec)
        val min = mWidth.coerceAtMost(mHeight)
        Log.d(TAG, "mWidth = $mWidth, mHeight = $mHeight")
        setMeasuredDimension(min, min)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 内圆
        val min = measuredWidth.coerceAtMost(measuredHeight)
        val center = (min/2).toFloat()
        canvas?.drawCircle(measuredWidth.toFloat()/2, measuredHeight.toFloat()/2, center-roundWidth, innerPaint)

        // 外圆
        rectF.set(0 + roundWidth.toFloat(), 0 + roundWidth.toFloat(), measuredWidth - roundWidth.toFloat(), measuredHeight - roundWidth.toFloat())
        if (progress == 0F) {
            return
        }
        val percent = progress/maxProgress
        Log.d(TAG, "percent = $percent")
        canvas?.drawArc(rectF, 0F, percent*360, false, outerPaint)

        // 进度文字
        val text = ((percent*100).toInt().toString() + "%")
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        val x = width/2 - textBounds.width()/2
        val fontMetrics = textPaint.fontMetricsInt
        val dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom
        val baseLineY = measuredHeight/2 + dy
        canvas?.drawText(text, x.toFloat(), baseLineY.toFloat(), textPaint)
    }

    fun setMax(max: Int) {
        if (max < 0) {
            Log.d(TAG, "setMax: max<0 不合法 $max")
        }
        maxProgress = max.toFloat()
    }

    @Synchronized
    fun setProgress(process: Int) {
        if (process < 0) {
            Log.d(TAG, "setMax: process<0 不合法 $process")
        }
        progress = process.toFloat()
        invalidate() //刷新
    }

    @Synchronized
    fun getProgress() : Int = progress.toInt()
}
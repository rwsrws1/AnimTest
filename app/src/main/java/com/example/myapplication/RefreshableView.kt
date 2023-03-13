package com.example.myapplication

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pull_to_refresh.view.*

class RefreshableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), View.OnTouchListener {
    @SuppressLint("InflateParams")
    private val header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true)
    private lateinit var recyclerView: RecyclerView
    private lateinit var hLayoutParams: MarginLayoutParams
    private var headerHeight = 0
    private var xDown = 0F
    private var yDown = 0F
    private var loadOnce = false
    private var mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MESSAGE_WHAT -> {
                    val list = ArrayList<News>()
                    for(i in 0 until 20){
                        var name = "刷新新闻+${i}"
                        var details = "内容是我是第${i}条新闻啊啊啊啊啊啊啊啊啊啊啊啊啊"
                        var news = News(0, name, details)
                        list.add(news)
                    }
                    (recyclerView.adapter as RvAdapter).replaceAllItem(list)
                    hLayoutParams.topMargin = -headerHeight
                    header.layoutParams = hLayoutParams
                    arrow_iv.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                    tv_top.text = resources.getString(R.string.pull_to_refresh)
                }
            }
        }
    }

    init {
        Log.d(TAG, "RefreshableView init")
        addView(header, 0)
    }

    companion object {
        private val TAG = RefreshableView::class.java.simpleName
        private const val MESSAGE_WHAT = 1
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed && !loadOnce) {
            recyclerView = getChildAt(1) as RecyclerView
            recyclerView.setOnTouchListener(this)
            headerHeight = header.measuredHeight
            Log.d(TAG, "headerHeight =  $headerHeight")
            hLayoutParams = header.layoutParams as MarginLayoutParams
            hLayoutParams.topMargin = -headerHeight
            header.layoutParams = hLayoutParams
            loadOnce = true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event == null) return false
        val firstChild = recyclerView.getChildAt(0)
        if (firstChild.top != 0 && hLayoutParams.topMargin == -headerHeight) {
            return false
        }
        val x:Float = event.rawX
        val y:Float = event.rawY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                xDown = x
                yDown = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (hLayoutParams.topMargin == 0) {
                    tv_top.text = resources.getString(R.string.release_to_refresh)
                    tv_top.setTextColor(Color.DKGRAY)
                } else {
                    if (tv_top.text == resources.getString(R.string.release_to_refresh)) {
                        tv_top.text = resources.getString(R.string.pull_to_refresh)
                        tv_top.setTextColor(Color.GRAY)
                    }
                }
                val distance = y - yDown
                hLayoutParams.topMargin = -headerHeight + (distance/3).toInt()
                hLayoutParams.topMargin = hLayoutParams.topMargin.coerceAtMost(0).coerceAtLeast(-headerHeight)
                header.layoutParams = hLayoutParams
            }
            MotionEvent.ACTION_UP -> {
                if (hLayoutParams.topMargin == 0) {
                    arrow_iv.visibility = View.GONE
                    tv_top.text = resources.getString(R.string.refreshing)
                    progress_bar.visibility = View.VISIBLE
                    mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000)
                } else {
                    val valueAnimator = ValueAnimator.ofInt(hLayoutParams.topMargin, -headerHeight)
                    valueAnimator.duration = 1000
                    valueAnimator.addUpdateListener {
                        hLayoutParams.topMargin = it.animatedValue as Int
                        header.layoutParams = hLayoutParams
                    }
                    valueAnimator.start()
                }
            }
        }
        if (hLayoutParams.topMargin > -headerHeight) {
            return true
        }
        return false
    }
}
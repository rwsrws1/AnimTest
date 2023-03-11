package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread

class RefreshableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), View.OnTouchListener {
    @SuppressLint("InflateParams")
    private val header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true)
    private lateinit var recyclerView: RecyclerView
    private lateinit var hLayoutParams: MarginLayoutParams

    init {
        addView(header, 0)
    }

    companion object {
        private val TAG = RefreshableView::class.java.simpleName
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        hLayoutParams = header.layoutParams as MarginLayoutParams
        hLayoutParams.topMargin = -header.measuredHeight + 100
        recyclerView = getChildAt(1) as RecyclerView
        recyclerView.setOnTouchListener(this)
        super.onLayout(changed, l, t, r, b)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouch")
        if (event == null) return false
        var x:Float = event.x
        var y:Float = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "ACTION_DOWN $x $y")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "ACTION_MOVE $x $y")
                var lp = LayoutParams(LayoutParams.MATCH_PARENT, 60)
                lp.topMargin = 0
                header.layoutParams = lp
            }
        }
        return false
    }
}
package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.top_bar.view.*

class TopLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    init {
        initView(context)
    }

    companion object {
        private val TAG = TopLayout::class.simpleName
    }

    private fun initView(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.top_bar, this, true)
        tittle_tv.setTextColor(Color.BLUE)
        setOnclick()
    }

    private fun setOnclick() {
        back_btn.setOnClickListener {
            (context as Activity).finish()
        }
    }

    fun registerClick(headListener: HeadListener) {
        head_iv.setOnTouchListener { v, _ ->
            headListener.onclick()
            v.performClick()
            return@setOnTouchListener false
        }
    }

    interface HeadListener {
        fun onclick()
    }
}
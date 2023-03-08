package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LayoutAnimationController

class AnimationController : LayoutAnimationController {

    private lateinit var onIndexListener: Callback

    companion object {
        const val ORDER_CUSTOM = 7
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(animation: Animation, delay: Float) : super(animation, delay)

    constructor(animation: Animation) : super(animation)

    override fun getTransformedIndex(params: AnimationParameters): Int {
        return if (order == ORDER_CUSTOM) {
            onIndexListener.onIndex(this, params.count, params.index)
        } else {
            super.getTransformedIndex(params)
        }
    }

    fun setOnIndexListener(onIndexListener: Callback) {
        this.onIndexListener = onIndexListener
    }

    interface Callback {
        fun onIndex(controller: AnimationController?, count: Int, index: Int): Int
    }
}
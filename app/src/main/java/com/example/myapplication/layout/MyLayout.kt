package com.example.myapplication.layout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup

class MyLayout : ViewGroup {
    private var margin = 0

    companion object {
        private val TAG = MyLayout::class.simpleName
    }

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val w = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val h = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        Log.d(TAG, "setMeasureDimension $w $h")
        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "onLayout $changed $l $t $r $b")
        var childWidth: Int //子控件宽度
        var childHeight: Int //子控件高度
        var myLayoutWidth = margin //MyLayout当前宽度
        var myLayoutHeight = margin //MyLayout当前高度
        var maxHeight = 0 //

        var layoutLeft: Int
        var layoutRight: Int
        var layoutTop: Int
        var layoutBottom: Int

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            childWidth = child.measuredWidth
            childHeight = child.measuredHeight
            if (context.resources.displayMetrics.widthPixels - myLayoutWidth > childWidth) {
                layoutLeft = myLayoutWidth
                layoutRight = layoutLeft + childWidth
                layoutTop = myLayoutHeight
                layoutBottom = layoutTop + childHeight
            } else {
                myLayoutWidth = margin
                myLayoutHeight += maxHeight
                maxHeight = 0

                layoutLeft = myLayoutWidth
                layoutRight = layoutLeft + childWidth
                layoutTop = myLayoutHeight
                layoutBottom = layoutTop + childHeight
            }
            myLayoutWidth += childWidth + margin
            if (childHeight > maxHeight) {
                maxHeight = childHeight + margin
            }

            child.layout(layoutLeft, layoutTop, layoutRight, layoutBottom)
            Log.d(TAG, "child.layout $layoutLeft, $layoutTop, $layoutRight, $layoutBottom ")
        }
    }

    fun setMargin(margin : Int) {
        this.margin = margin
        invalidate()
    }
}
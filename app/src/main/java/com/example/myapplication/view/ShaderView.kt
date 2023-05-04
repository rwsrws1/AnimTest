package com.example.myapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R

class ShaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.shader)
    private val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

    init {
        paint.shader = shader
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.textSize = 230F
        paint.style = Paint.Style.FILL
        canvas.drawText("Hello World", 100F, 500F, paint)
        canvas.drawCircle(200F, 200F, 100F, paint)
        canvas.drawRect(400F, 100F, 800F, 300F, paint)
    }
}
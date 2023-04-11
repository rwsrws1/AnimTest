package com.example.myapplication

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapter.AnimationController
import com.example.myapplication.layout.MyLayout


class MainActivity3 : AppCompatActivity() {
    private var myLayout: MyLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        initData()
        initView()
        setOnClick()
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale)

//        val layoutAnimation = LayoutAnimationController(animation)
//        layoutAnimation.delay = 0.05F
//        layoutAnimation.order = LayoutAnimationController.ORDER_NORMAL
//        myLayout?.layoutAnimation = layoutAnimation

        val mController = AnimationController(animation, 0.2F)
        mController.order = AnimationController.ORDER_CUSTOM
        mController.setOnIndexListener(object : AnimationController.Callback {
            override fun onIndex(controller: AnimationController?, count: Int, index: Int): Int {
                when(index%4) {
                    0 -> return 0 + index/4
                    1 -> return 1 + index/4
                    2 -> return 2 + index/4
                    3 -> return 3 + index/4
                }
                return index
            }
        })
        myLayout?.layoutAnimation = mController
    }

    private fun initData() {
    }

    private fun initView() {
        myLayout = findViewById(R.id.my_layout)
        myLayout?.setMargin(5)
    }

    private fun setOnClick() {
    }
}
package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.util.Log
import android.view.Window
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_bar.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), TopLayout.HeadListener {
    private lateinit var middleView: MiddleView
    private lateinit var topView: TopLayout
    private lateinit var testBtn: Button

    companion object{
        val TAG = MainActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.exitTransition = Slide()
        setContentView(R.layout.activity_main)
        initView()
        initData()
        setOnclick()
    }

    private fun initView() {
        middleView = findViewById(R.id.progress_bar)
        topView = findViewById(R.id.top_view)
        testBtn = findViewById(R.id.test_btn)
        middleView.setMax(1000)
    }

    private fun initData() {
        topView.registerClick(this)
    }

    private fun setOnclick() {
        testBtn.setOnClickListener {
            if (middleView.getProgress() == 0) {
                startTest()
            }
        }
        btn_activity4.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }
        btn_activity5.setOnClickListener {
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }
        btn_activity6.setOnClickListener {
            val intent = Intent(this, MainActivity6::class.java)
            startActivity(intent)
        }
        btn_activity7.setOnClickListener {
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }
    }

    private fun startTest() {
        Log.d(TAG, "tittle_tv.textSize = ${tittle_tv.textSize}")
        val animator = ObjectAnimator.ofFloat(0F, 1000F)
        animator.duration = 2000
        animator.start()
        animator.addUpdateListener { animation ->
            val progress = animation?.animatedValue
            middleView.setProgress((progress as Float).toInt())
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(intent)
                thread {
                    Thread.sleep(500)
                    middleView.setProgress(0)
                }
            }
        })
    }

    override fun onclick() {
        Log.d(TAG, "YOU CLICK HEAD")
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}
package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.util.Pair
import android.view.animation.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.top_bar.*

class MainActivity2 : AppCompatActivity(), TopLayout.HeadListener{
    private val animationSet = AnimationSet(true)
    private var recyclerView: RecyclerView? = null
    private lateinit var newsList: ArrayList<News>
    private var tranView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.enterTransition = Slide()
        window.exitTransition = Explode()
        setContentView(R.layout.activity_main2)
        tranView = findViewById(R.id.text_tv3)
        initAnimation()
        setOnclick()
        text_tv1.startAnimation(animationSet)
    }

    private fun setOnclick() {
        back_btn.setOnClickListener { finish() }
        head_iv.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(tranView, "tran_1"))
                .toBundle())
        }
    }

    private fun initAnimation() {
        val translate = TranslateAnimation(0F, 400F, 0F, 800F)
        translate.duration = 10000
        translate.fillBefore = true
        translate.repeatCount = -1
        translate.repeatMode = Animation.REVERSE
        val alpha = AlphaAnimation(1F, 0.5F)
        alpha.duration = 10000
        alpha.repeatMode = Animation.REVERSE
        alpha.repeatCount = -1
        val rotate = RotateAnimation(0F, -15F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 5000
        rotate.repeatMode = Animation.REVERSE
        rotate.repeatCount = -1
        val scale = ScaleAnimation(1F, 1.5F, 1F, 2F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f)
        scale.duration = 10000
        scale.repeatMode = Animation.REVERSE
        scale.repeatCount = -1
        animationSet.apply {
            addAnimation(translate)
            addAnimation(alpha)
            addAnimation(scale)
            addAnimation(rotate)
            startOffset = 5000
            interpolator = OvershootInterpolator()
        }

        val objectAnimator = ObjectAnimator.ofInt(text_tv2, "textColor",
            0xffFFFFFF.toInt(), 0xff000000.toInt()
        )
        objectAnimator.duration = 50000
        objectAnimator.repeatCount = Animation.INFINITE
        objectAnimator.repeatMode = ValueAnimator.REVERSE

        val objectAnimator2 = ObjectAnimator.ofFloat(text_tv2, "rotationX", 0F, 500F)
        objectAnimator2.duration = 5000
        objectAnimator2.repeatCount = Animation.INFINITE
        objectAnimator2.repeatMode = ValueAnimator.REVERSE

        val animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator2).with(objectAnimator)
        animatorSet.start()
    }

    override fun onclick() {
        val intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }
}
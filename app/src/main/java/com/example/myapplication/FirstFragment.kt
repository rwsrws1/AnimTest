package com.example.myapplication

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout.CENTER_IN_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        // Create a button and set its text
        val button = Button(context).apply {
            text = "Start Progress"
        }

// Set the button's layout parameters
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            leftToLeft = R.id.parent
            rightToRight = R.id.parent
            topToTop = R.id.parent
            bottomToBottom = R.id.parent
        }

// Add the button to the view
        binding.root.addView(button, params)

// Set a click listener for the button
        button.setOnClickListener {
           startProgress()
        }

        val button2 = Button(context).apply {
            text = "Clear Progress"
        }.apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
               // 位于button1的右边
                leftToRight = button.id
                rightToRight = R.id.parent
                topToTop = R.id.parent
                bottomToBottom = R.id.parent
            }
        }.apply {
            setOnClickListener {
                clearProgress()
            }
        }
        // button2添加到布局
        binding.root.addView(button2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startProgress() {
        // Animate the progress from 0 to 100 over 1 second
        val animator = ValueAnimator.ofInt(0, 100).apply {
            duration = 1000
            addUpdateListener {
                binding.ivProgressBar.setProgress(it.animatedValue as Int)
            }
        }
        animator.start()
    }

    private fun clearProgress() {
        binding.ivProgressBar.setProgress(0)
    }
}
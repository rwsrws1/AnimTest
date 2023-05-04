package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.adapter.FragAdapter
import com.example.myapplication.databinding.ActivityMain10Binding
import com.example.myapplication.fragment.BlankFragment
import com.example.myapplication.fragment.BlankFragment2
import com.example.myapplication.fragment.BlankFragment3
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity10 : AppCompatActivity() {
    private val binding: ActivityMain10Binding by lazy {
        ActivityMain10Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fragmentList = arrayListOf(
            BlankFragment(),
            BlankFragment2(),
            BlankFragment3()
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = "Tab ${position + 1}"
        }.attach()
        val adapter = FragAdapter(this, fragmentList)
        binding.viewPager2.adapter = adapter
    }
}
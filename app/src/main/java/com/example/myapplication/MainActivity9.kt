package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.fragment.MyFragment
import com.example.myapplication.fragment.MyFragment2

class MainActivity9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = MyFragment()
        val fragment2 = MyFragment2()
        setContentView(R.layout.activity_main9)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .commit()

        supportFragmentManager.setFragmentResult("requestKey", Bundle().apply {
            putString("bundleKey", "bundleValue")
        })

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment2)
            .addToBackStack("back")
            .commit()
    }
}
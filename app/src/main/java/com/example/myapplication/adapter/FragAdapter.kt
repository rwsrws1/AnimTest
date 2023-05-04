package com.example.myapplication.adapter

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragAdapter(activity: FragmentActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(activity) {
    private val fid1 = 111L
    private val fid2 = 222L
    private val fid3 = 333L
    private val ids = arrayListOf(fid1,fid2, fid3)
    private val createID = hashSetOf<Long>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val id = ids[position]
        createID.add(id)
        val fragment = fragmentList[position]
        fragment.arguments = Bundle().apply {
            putInt("mKey", position + 1)
        }
        return fragment
    }

    override fun containsItem(itemId: Long): Boolean {
        return createID.contains(itemId)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
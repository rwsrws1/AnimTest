package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.RvAdapter2
import com.example.myapplication.callback.DragCallBack
import com.example.myapplication.data.Card
import com.example.myapplication.databinding.ActivityMain7Binding

class MainActivity7 : AppCompatActivity() {
    private lateinit var mBinding: ActivityMain7Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMain7Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.topBar.backBtn.setOnClickListener {
            finish()
        }
        initRecycleView()
        initRecycleView2()
    }

    companion object {
        private val TAG = MainActivity7::class.java.simpleName
    }

    private fun initRecycleView() {
        val list = ArrayList<Card>()
        for (i in 0 until 100) {
            list.add(Card(0, "卡牌$i"))
        }
        val adapter2 = RvAdapter2(list)
        mBinding.dragView.apply {
            adapter = adapter2
            layoutManager = GridLayoutManager(context, 5)
        }
        val dragCallBack = DragCallBack(adapter2, list)
        val itemTouchHelper = ItemTouchHelper(dragCallBack)
        itemTouchHelper.attachToRecyclerView(mBinding.dragView)
    }

    private fun initRecycleView2() {
        val list = ArrayList<Card>()
        for (i in 0 until 10) {
            list.add(Card(0, "我的卡牌$i"))
        }
        val adapter2 = RvAdapter2(list)
        mBinding.dragView2.apply {
            adapter = adapter2
            layoutManager = LinearLayoutManager(context)
        }
        adapter2.setCardLayout(200, 50)
        val dragCallBack = DragCallBack(adapter2, list)
        val itemTouchHelper = ItemTouchHelper(dragCallBack)
        itemTouchHelper.attachToRecyclerView(mBinding.dragView2)
    }
}
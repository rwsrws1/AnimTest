package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main6.*

class MainActivity6 : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private lateinit var newsList: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)
        initRecyclerView()
    }

    companion object {
        private val TAG = MainActivity6::class.java.simpleName
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.rv_news)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        newsList = ArrayList()
        for(i in 0 until 20){
            var name = "新闻+${i}"
            var details = "内容是我是第${i}条新闻啊啊啊啊啊啊啊啊啊啊啊啊啊"
            var news = News(0, name, details)
            newsList.add(news)
        }
        Log.d(TAG, "newsList.size = ${newsList.size}")
        var adapter = RvAdapter(newsList)
        recyclerView?.adapter = adapter
        bt_add_news.setOnClickListener {
            Toast.makeText(this, "YOU ADD NEWS", Toast.LENGTH_SHORT).show()
            adapter.addItem(News(0, "新增新闻", "新闻内容"))
            recyclerView?.smoothScrollToPosition(newsList.size - 1)
        }
        bt_delete_news.setOnClickListener {
            adapter.deleteItem(newsList.size - 1)
        }
        bt_top_news.setOnClickListener {
            recyclerView?.scrollToPosition(0)
        }
    }
}
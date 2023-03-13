package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter(var mList: MutableList<News>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var newPicture: ImageView = view.findViewById(R.id.new_picture)
        var newName: TextView = view.findViewById(R.id.news_name)
        var newDetail: TextView = view.findViewById(R.id.new_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var news = mList[position]
        holder.newName.text = news.name
        holder.newDetail.text = news.detail
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun addItem(news: News) {
        mList.add(news)
        notifyItemInserted(mList.size - 1)
    }

    fun deleteItem(position: Int) {
        if (position < 0) {
            return
        }
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun replaceAllItem(list: ArrayList<News>) {
        mList.clear()
        mList = list
        notifyItemRangeChanged(0, list.size - 1)
    }
}
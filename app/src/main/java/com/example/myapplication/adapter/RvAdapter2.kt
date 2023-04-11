package com.example.myapplication.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Card
import com.example.myapplication.databinding.Item2Binding

class RvAdapter2(private var list: MutableList<Card>) : RecyclerView.Adapter<RvAdapter2.ViewHolder>() {
    private lateinit var mContext: Context
    private var mWidth = 100
    private var mHeight = 100
    private val fixedPosition = 999

    inner class ViewHolder(binding: Item2Binding) : RecyclerView.ViewHolder(binding.root) {
//        var newPicture: ImageView = itemView.findViewById(R.id.new_picture)
        var carPicture: ImageView = binding.cardPicture
        var carName: TextView = binding.cardName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = Item2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        mContext = parent.context
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = list[position]
        holder.carPicture.setImageResource(card.picture)
        holder.carName.text = card.name
        holder.itemView.layoutParams.width = dip2px(mWidth)
        holder.itemView.layoutParams.height = dip2px(mHeight)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun dip2px(dip: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), mContext.resources.displayMetrics).toInt()
    }

    fun getFixedPosition(): Int {
        return fixedPosition
    }

    fun setCardLayout(width: Int, height: Int) {
        mWidth = width
        mHeight = height
    }
}
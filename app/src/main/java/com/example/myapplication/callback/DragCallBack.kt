package com.example.myapplication.callback

import android.annotation.SuppressLint
import android.graphics.drawable.RippleDrawable
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.RvAdapter2
import com.example.myapplication.data.Card
import java.util.*

class DragCallBack(var mAdapter: RvAdapter2, var mData: MutableList<Card>) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var dragFlags = 0
        var swipeFlags = 0
        return when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN
                makeMovementFlags(dragFlags, swipeFlags)
            }
            is LinearLayoutManager -> {
                dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                swipeFlags = ItemTouchHelper.END
                makeMovementFlags(dragFlags, swipeFlags)
            }
            else -> {
                0
            }
        }
    }

    companion object {
        private val TAG = DragCallBack::class.java.simpleName
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.d(TAG, "onMove ${viewHolder.adapterPosition} ${target.adapterPosition}")
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        if (fromPosition == mAdapter.getFixedPosition() || toPosition == mAdapter.getFixedPosition()) {
            return false
        }
        if (fromPosition < toPosition) {
            for (index in fromPosition until toPosition) {
                Collections.swap(mData, index, index + 1)
            }
        } else {
            for (index in fromPosition downTo toPosition + 1)
                Collections.swap(mData, index, index - 1)
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.START) {
            Log.d(TAG, "START ---> 向左滑")
        } else {
            Log.d(TAG, "END --- 向右滑")
            mData.removeAt(position)
            mAdapter.notifyItemRemoved(position)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        Log.d(TAG, "onSelectedChanged, itemView = ${viewHolder?.itemView}")
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.let {
                if (it.itemView.width > 500) {
                    val drawable = it.itemView.background as RippleDrawable
                    it.itemView.alpha = 0.6F
                    drawable.alpha = 100
                } else {
                    ViewCompat.animate(it.itemView).setDuration(200).scaleX(1.3F).scaleY(1.3F).start()
                }
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        Log.d(TAG, "clearView, itemView = ${viewHolder.itemView}")
        when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                ViewCompat.animate(viewHolder.itemView).setDuration(200).scaleX(1F).scaleY(1F).start()
            }
            is LinearLayoutManager -> {
                val drawable = viewHolder.itemView.background as RippleDrawable
                viewHolder.itemView.alpha = 1F
                drawable.alpha = 255
            }
        }
        super.clearView(recyclerView, viewHolder)
    }
}
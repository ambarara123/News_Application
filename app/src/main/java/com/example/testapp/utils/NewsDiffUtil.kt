package com.example.testapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp.network.model.RoomResult

class NewsDiffUtil(private val oldList: List<RoomResult>, private val newList: List<RoomResult>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }
}
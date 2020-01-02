package com.example.testapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ListItemBinding
import com.example.testapp.network.model.RoomResult

class MainRecyclerAdapter :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    private var data: List<RoomResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    fun updateDatSet(roomResults: List<RoomResult>) {
        data = roomResults
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind()
    }

    inner class MainViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val roomResult = data[adapterPosition]
            with(binding) {
                sectionTitle.text = roomResult.title
                sectionName.text = roomResult.section
                sectionAbstract.text = roomResult.abs
            }
        }
    }
}
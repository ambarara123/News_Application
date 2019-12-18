package com.example.testapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ProgressbarItemBinding
import com.example.testapp.databinding.SearchListBinding
import com.example.testapp.model.search.Doc

class SearchPagedAdapter : PagedListAdapter<Doc, SearchPagedAdapter.ViewHolder>(SearchDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SearchListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(private val binding : SearchListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(doc : Doc){

            with(binding){
                abstractDocs.text = doc.abstract
                paragraph.text = doc.lead_paragraph
            }
        }
    }

    companion object {
        val SearchDiffCallback = object : DiffUtil.ItemCallback<Doc>() {
            override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.example.testapp.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.BooksListBinding
import com.example.testapp.network.model.books.BookRoom
import com.squareup.picasso.Picasso

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private var bookList: List<BookRoom> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BooksListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun updateDataSet(books: List<BookRoom>) {
        bookList = books
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: BooksListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val book = bookList[adapterPosition]
            with(binding) {
                Picasso.with(root.context).load(book.book_image).into(bookImage)
                booksName.text = book.title
                bookAuthor.text = book.author
                description.text = book.description
            }
        }
    }
}
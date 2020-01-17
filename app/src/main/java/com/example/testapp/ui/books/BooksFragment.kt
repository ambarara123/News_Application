package com.example.testapp.ui.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentBooksBinding
import com.example.testapp.network.model.books.BookRoom
import com.example.testapp.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class BooksFragment : BaseFragment<FragmentBooksBinding, BooksViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_books

    override fun getViewModelClass(): Class<BooksViewModel> = BooksViewModel::class.java

    companion object {
        private var INSTANCE: BooksFragment? = null
        fun getInstance(): BooksFragment {
            if (INSTANCE == null)
                INSTANCE = BooksFragment()
            return INSTANCE!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        addListeners()
        addObservers()
        viewModel.getData()
    }

    private fun initRecycler() {
        with(binding.bookRecyclerView) {
            layoutManager =
                LinearLayoutManager(context)
            adapter = BooksAdapter()
        }
    }

    private fun addListeners() {
        binding.booksSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getData()
        }
    }

    private fun addObservers() {
        viewModel.booksLiveData.observe(viewLifecycleOwner, Observer {
            stopRefreshing()
            updateRecyclerViewAdapter(it)
        })
    }

    private fun updateRecyclerViewAdapter(roomResults: List<BookRoom>) {
        val adapter = binding.bookRecyclerView.adapter
        (adapter as? BooksAdapter)?.updateDataSet(roomResults)
    }

    private fun stopRefreshing() {
        with(binding.booksSwipeRefreshLayout) {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
    }

}

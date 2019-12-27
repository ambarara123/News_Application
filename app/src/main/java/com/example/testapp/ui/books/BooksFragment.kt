package com.example.testapp.ui.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.testapp.R
import com.example.testapp.databinding.FragmentBooksBinding
import com.example.testapp.model.books.BookRoom
import com.example.testapp.ui.base.BaseFragment
import com.example.testapp.utils.isNetworkAvailable


/**
 * A simple [Fragment] subclass.
 */
class BooksFragment : BaseFragment<FragmentBooksBinding, BooksViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_books

    override fun getViewModelClass(): Class<BooksViewModel> = BooksViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMainRecycler()
        addListeners()
        addObservers()
        viewModel.getData(isConnected())

    }

    private fun initMainRecycler() {
        with(binding) {
            bookRecyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(context)
            bookRecyclerView.adapter = BooksAdapter()

        }
    }

    private fun addListeners() {
        with(binding) {
            booksSwipeRefreshLayout.setOnRefreshListener {
                viewModel?.getData(isConnected())
            }
        }
    }

    private fun addObservers() {
        with(binding) {
            viewModel!!.booksLiveData.observe(lifecycleOwner!!, Observer {
                stopRefreshing()
                updateRecyclerViewAdapter(it)
            })
        }
    }

    private fun updateRecyclerViewAdapter(roomResults: List<BookRoom>) {
        with(binding) {
            val adapter = bookRecyclerView.adapter
            if (adapter is BooksAdapter) {
                adapter.updateDataSet(roomResults)
            }
        }
    }

    private fun isConnected(): Boolean {
        return isNetworkAvailable(context!!)
    }

    private fun stopRefreshing() {
        with(binding) {
            if (booksSwipeRefreshLayout.isRefreshing) {
                booksSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

}

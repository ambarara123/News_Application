package com.example.testapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.ActivitySearchBinding
import com.example.testapp.network.model.search.Doc
import com.example.testapp.ui.base.BaseActivity

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        addObserver()
    }

    private fun initViews() {
        with(binding.searchView) {
            setOnCloseListener(this@SearchActivity)
            setOnQueryTextListener(this@SearchActivity)
            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view)
                }
            }
            requestFocus()
        }
        initRecyclerView()
    }

    private fun addObserver() {
        viewModel.searchLiveData.observe(this, Observer {
            updateRecyclerViewAdapter(it)
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.getData(query!!)
        dismissKeyboard(this.currentFocus!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onClose(): Boolean {
        finish()
        return true
    }

    private fun initRecyclerView() {
        with(binding.searchRecyclerView) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = SearchPagedAdapter()
        }
    }

    private fun showKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    private fun dismissKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun updateRecyclerViewAdapter(list: PagedList<Doc>) {
        val adapter = binding.searchRecyclerView.adapter
        (adapter as? SearchPagedAdapter)?.submitList(list)
    }
}

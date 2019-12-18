package com.example.testapp.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.testapp.NewsApplication

import com.example.testapp.R
import com.example.testapp.databinding.FragmentNewsBinding
import com.example.testapp.model.RoomResult
import com.example.testapp.ui.base.BaseFragment
import com.example.testapp.utils.isNetworkAvailable

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment<FragmentNewsBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment
        initMainRecycler()
        addObservers()
        addListeners()
        viewModel.getDataCoroutine(isConnected())

    }

    private fun initMainRecycler() {
        with(binding) {
            recyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(context)
            recyclerView.adapter = MainRecyclerAdapter()
        }
    }

    private fun addListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDataCoroutine(isConnected())
        }
    }

    private fun addObservers() {
        viewModel.storiesLiveData.observe(viewLifecycleOwner, Observer {
            stopRefreshing()
            updateRecyclerViewAdapter(it)
        })
    }

    private fun updateRecyclerViewAdapter(roomResults: List<RoomResult>) {
        val adapter = binding.recyclerView.adapter
        if (adapter is MainRecyclerAdapter) {
            adapter.updateDatSet(roomResults)
        }
    }

    private fun isConnected(): Boolean {
        return isNetworkAvailable(context!!)
    }

    private fun stopRefreshing() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}

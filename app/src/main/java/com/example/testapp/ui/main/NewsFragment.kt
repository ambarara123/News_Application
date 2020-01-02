package com.example.testapp.ui.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNewsBinding
import com.example.testapp.network.model.RoomResult
import com.example.testapp.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment<FragmentNewsBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataCoroutine()
    }

    override fun initRecycler() {
        with(binding.recyclerView) {
            layoutManager =
                LinearLayoutManager(context)
            adapter = MainRecyclerAdapter()
        }
    }

    override fun addListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDataCoroutine()
        }
    }

    override fun addObservers() {
        viewModel.storiesLiveData.observe(viewLifecycleOwner, Observer {
            stopRefreshing()
            updateRecyclerViewAdapter(it)
            stopRefreshing()
        })
    }

    private fun updateRecyclerViewAdapter(results: List<RoomResult>) {
        val adapter = binding.recyclerView.adapter

        (adapter as? MainRecyclerAdapter)?.updateDatSet(results)
    }

    private fun stopRefreshing() {
        with(binding.swipeRefreshLayout) {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
    }
}

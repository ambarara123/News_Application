package com.example.testapp.ui.main


import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNewsBinding
import com.example.testapp.network.model.RoomResult
import com.example.testapp.ui.base.BaseFragment
import com.example.testapp.utils.KEY_INDEX
import com.example.testapp.utils.KEY_TOP_PADDING
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment<FragmentNewsBinding, MainViewModel>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_news

    companion object {
        private var INSTANCE: NewsFragment? = null
        fun getInstance(): NewsFragment {
            if (INSTANCE == null)
                INSTANCE = NewsFragment()
            return INSTANCE!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataCoroutine()
    }

    override fun onDestroyView() {
        Timber.d("On Destroy view")
        saveRecyclerPosition()
        super.onDestroyView()
    }

    override fun onDetach() {
        Timber.d("On Detach")
        saveRecyclerPosition()
        super.onDetach()
    }

    private fun saveRecyclerPosition() {
        with(binding.recyclerView) {

            val index =
                (layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                    ?: -1
            val view = getChildAt(0)

            val recyclerTopPadding = if (view == null) 0
            else (view.top - paddingTop)

            Timber.d("$index  $recyclerTopPadding")

            savePositionInSharedPreferences(index, recyclerTopPadding)
        }
    }

    private fun setRecyclerPosition() {
        val index = sharedPreferences.getInt(KEY_INDEX, -1)
        val topPadding = sharedPreferences.getInt(KEY_TOP_PADDING, 0)

        if (index != -1)
            (binding.recyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                index,
                topPadding
            )

    }

    private fun savePositionInSharedPreferences(index: Int, topPadding: Int) {
        sharedPreferences.edit().apply {
            putInt(KEY_INDEX, index)
            putInt(KEY_TOP_PADDING, topPadding)
        }.apply()
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
        setRecyclerPosition()
    }

    private fun stopRefreshing() {
        with(binding.swipeRefreshLayout) {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
    }
}

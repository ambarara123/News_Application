package com.example.testapp.ui.main


import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        addListeners()
        addObservers()
        viewModel.getDataCoroutine()
    }

    override fun onPause() {
        super.onPause()
        saveRecyclerPosition()
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

        if (index != -1) {
            (binding.recyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                (index - 1),
                topPadding
            )
            savePositionInSharedPreferences(-1, 0)
        }
    }

    private fun savePositionInSharedPreferences(index: Int, topPadding: Int) {
        sharedPreferences.edit()
            .putInt(KEY_INDEX, index)
            .putInt(KEY_TOP_PADDING, topPadding)
            .apply()
    }

    private fun initRecycler() {
        with(binding.recyclerView) {
            layoutManager =
                LinearLayoutManager(context)
            adapter = MainRecyclerAdapter()
        }
    }

    private fun addListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDataCoroutine()
        }
    }

    private fun addObservers() {
        viewModel.storiesLiveData.observe(viewLifecycleOwner, Observer {
            stopRefreshing()
            updateRecyclerViewAdapter(it)
            stopRefreshing()
        })
    }

    private fun updateRecyclerViewAdapter(results: List<RoomResult>) {
        val adapter = binding.recyclerView.adapter

        val updatedPosition = (adapter as? MainRecyclerAdapter)?.updateDataSet(results).also {
            setRecyclerPosition()
        }
        //MARK: new data loaded
        if (updatedPosition != -1) {
            Toast.makeText(context, "new data loaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRefreshing() {
        with(binding.swipeRefreshLayout) {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
    }
}

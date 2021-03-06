package com.example.testapp.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.BR
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : DaggerAppCompatActivity() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: B

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView(getLayoutId())
    }

    private fun bindView(layoutRes: Int) {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
        binding.setVariable(BR.viewModel, viewModel)

    }

    abstract fun getViewModelClass(): Class<VM>

    @LayoutRes
    abstract fun getLayoutId(): Int

}

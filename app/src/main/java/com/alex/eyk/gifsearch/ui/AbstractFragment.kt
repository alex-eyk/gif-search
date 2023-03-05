package com.alex.eyk.gifsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alex.eyk.gifsearch.BR
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class AbstractFragment<VM : ViewModel, B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate<B>(
            inflater,
            layoutRes,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        onBindingCreated()
        onInitVariables()
        collectStates()
        return binding.root
    }

    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                state = Lifecycle.State.STARTED,
                block = onCollectStates()
            )
        }
    }

    protected open fun onInitVariables() {
        with(binding) {
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
        }
    }

    protected open fun onBindingCreated() {}

    protected open fun onCollectStates(): suspend CoroutineScope.() -> Unit = {}

    protected fun quickSnackbar(
        @StringRes messageResId: Int
    ) {
        Snackbar.make(
            requireView(),
            messageResId,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}

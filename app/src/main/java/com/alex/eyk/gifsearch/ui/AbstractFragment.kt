package com.alex.eyk.gifsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class AbstractFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    protected lateinit var binding: B

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
        return binding.root
    }

    protected open fun onBindingCreated() {}
}

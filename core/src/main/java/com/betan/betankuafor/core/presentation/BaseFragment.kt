package com.betan.betankuafor.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

class BaseFragment<VBinding : ViewBinding, VModel : BaseViewModel>(
    private val bindingInflater: (inflater: LayoutInflater) -> VBinding,
    private val viewModelClass: Class<VModel>,
) : Fragment() {

    open val useSharedViewModel: Boolean = false

    val binding: VBinding by lazy {
        bindingInflater.invoke(layoutInflater)
    }

    val viewModel: VModel by lazy {
        if (useSharedViewModel) {
            ViewModelProvider(requireActivity())[viewModelClass]
        } else {
            ViewModelProvider(this)[viewModelClass]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}
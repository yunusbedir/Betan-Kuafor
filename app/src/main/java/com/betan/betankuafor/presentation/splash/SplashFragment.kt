package com.betan.betankuafor.presentation.splash

import com.betan.betankuafor.core.presentation.BaseFragment
import com.betan.betankuafor.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    bindingInflater = FragmentSplashBinding::inflate,
    viewModelClass = SplashViewModel::class.java
) {
}
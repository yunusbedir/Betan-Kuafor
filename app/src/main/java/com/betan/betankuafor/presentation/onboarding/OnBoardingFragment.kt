package com.betan.betankuafor.presentation.onboarding

import com.betan.betankuafor.core.presentation.BaseFragment
import com.betan.betankuafor.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding, OnBoardingViewModel>(
    bindingInflater = FragmentOnBoardingBinding::inflate,
    viewModelClass = OnBoardingViewModel::class.java
) {
}
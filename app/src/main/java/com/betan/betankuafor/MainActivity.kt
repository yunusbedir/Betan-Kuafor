package com.betan.betankuafor

import com.betan.betankuafor.core.presentation.BaseActivity
import com.betan.betankuafor.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override val isFullScreen: Boolean
        get() = true

}
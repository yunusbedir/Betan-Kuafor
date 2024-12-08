package com.betan.betankuafor.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VBinding : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VBinding,
) : AppCompatActivity() {

    val binding: VBinding by lazy {
        bindingInflater.invoke(layoutInflater)
    }

    open val isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            if (isFullScreen.not()) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            } else {
                v.setPadding(0, 0, 0, systemBars.bottom)
            }
            insets
        }
    }
}
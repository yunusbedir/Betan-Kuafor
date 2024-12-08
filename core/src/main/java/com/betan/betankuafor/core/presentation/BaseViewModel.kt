package com.betan.betankuafor.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betan.betankuafor.core.domain.UseCase
import com.betan.betankuafor.core.domain.UseCaseState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <PARAMS, RESULT> UseCase<PARAMS, RESULT>.action(
        params: PARAMS,
        result: (RESULT) -> Unit,
    ) {
        viewModelScope.launch {
            invoke(params).collectLatest { useCaseState ->
                when (useCaseState) {
                    is UseCaseState.Fail -> {
                        // fail state
                    }

                    is UseCaseState.Result -> {
                        result.invoke(useCaseState.data)
                    }
                }
            }
        }
    }
}
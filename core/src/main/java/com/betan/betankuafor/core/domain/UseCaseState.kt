package com.betan.betankuafor.core.domain

sealed class UseCaseState<T> {
    data class Result<T>(
        val data: T,
    ) : UseCaseState<T>()

    data class Fail<T>(
        val message: String,
    ) : UseCaseState<T>()

    fun isFail(): Boolean = this is Fail

    fun getDataOrNull(): T? = (this as? Result)?.data
}

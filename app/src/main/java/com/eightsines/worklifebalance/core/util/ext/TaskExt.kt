package com.eightsines.worklifebalance.core.util.ext

import bolts.Continuation
import bolts.Task
import java.util.concurrent.Callable
import java.util.concurrent.Executor

fun <T> Task<T>.makeUnit() = this.continueWithTask({ task ->
    when {
        task.isCancelled -> Task.cancelled()
        task.isFaulted -> Task.forError(task.error)
        else -> Task.forResult<Unit>(null)
    }
})!!

fun <TContinuationResult> Task<TContinuationResult>.continueOn(executor : Executor) : Task<TContinuationResult> =
        this.continueWithTask(Continuation { it }, executor)

fun <TResult, TContinuationResult> Task<TResult>.continueWithOn(
        executor : Executor,
        continuation : (Task<TResult>) -> TContinuationResult
) : Task<TContinuationResult> = this.continueWith(
        Continuation<TResult, TContinuationResult> { task -> continuation(task) },
        executor)

fun <TResult, TContinuationResult> Task<TResult>.continueWithTaskOn(
        executor : Executor,
        continuation : (Task<TResult>) -> Task<TContinuationResult>
) : Task<TContinuationResult> = this.continueWithTask(
        Continuation<TResult, Task<TContinuationResult>> { task -> continuation(task) },
        executor)

fun <TResult, TContinuationResult> Task<TResult>.onSuccessOn(
        executor : Executor,
        continuation : (Task<TResult>) -> TContinuationResult
) : Task<TContinuationResult> = this.onSuccess(
        Continuation<TResult, TContinuationResult> { task -> continuation(task) },
        executor)

fun <TResult, TContinuationResult> Task<TResult>.onSuccessTaskOn(
        executor : Executor,
        continuation : (Task<TResult>) -> Task<TContinuationResult>
) : Task<TContinuationResult> = this.onSuccessTask(
        Continuation<TResult, Task<TContinuationResult>> { task -> continuation(task) },
        executor)

object TaskExt {
    fun <TResult> callOn(executor : Executor, callable : () -> TResult) : Task<TResult> =
            Task.call(Callable { callable() }, executor)
}

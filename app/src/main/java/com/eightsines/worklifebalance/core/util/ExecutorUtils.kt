package com.eightsines.worklifebalance.core.util

import android.annotation.SuppressLint
import android.os.Build
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object ExecutorUtils {
    const val KEEP_ALIVE_TIME = 1L

    fun newFixedThreadPool(poolSize : Int) : ExecutorService {
        val executor = ThreadPoolExecutor(
                poolSize,
                poolSize,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                LinkedBlockingQueue<Runnable>()
        )

        allowCoreThreadTimeout(executor, true)
        return executor
    }

    @SuppressLint("NewApi", "ObsoleteSdkInt")
    private fun allowCoreThreadTimeout(executor : ThreadPoolExecutor, value : Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            executor.allowCoreThreadTimeOut(value)
        }
    }
}

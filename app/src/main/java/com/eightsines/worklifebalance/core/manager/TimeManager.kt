package com.eightsines.worklifebalance.core.manager

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeManager @Inject constructor() {
    companion object {
        const val SECOND_MILLIS = 1000L
    }

    fun getCurrentTimeMillis() = System.currentTimeMillis()
}

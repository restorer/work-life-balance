package com.eightsines.worklifebalance.core.entity

data class Goal(
        val id : Long,
        var position : Int,
        var name : String,
        var desiredTimeList : List<Int> = mutableListOf(),
        var totalTimeList : List<Int> = mutableListOf(),
        var credits : Long = 0,
        var lastActionTime : Long = 0,
        var isActive : Boolean = false) {

    val desiredTime : Int
        get() = if (desiredTimeList.isEmpty()) 0 else desiredTimeList.reduce { acc, i -> acc * i }

    val totalTime : Int
        get() = if (totalTimeList.isEmpty()) 0 else totalTimeList.reduce { acc, i -> acc * i }

    fun getCurrentCredits(currentTimeMillis : Long) : Long {
        if (desiredTime <= 0 || totalTime <= 0) {
            return 0L
        }

        if (lastActionTime <= 0) {
            return credits
        }

        return credits + if (isActive) {
            (currentTimeMillis - lastActionTime) / 1000L * (totalTime - desiredTime) / desiredTime
        } else {
            (lastActionTime - currentTimeMillis) / 1000L
        }
    }

    /**
     * If returned value is > 0, than this is a free time, during which user can achieve other goals.
     * If returned value is < 0, than this is the time in which to achieve this goal.
     */
    fun getDisplayCredits(currentTimeMillis : Long) : Long {
        val currentCredits = getCurrentCredits(currentTimeMillis)

        return if (currentCredits < 0) {
            currentCredits * desiredTime / (totalTime - desiredTime)
        } else {
            currentCredits
        }
    }
}

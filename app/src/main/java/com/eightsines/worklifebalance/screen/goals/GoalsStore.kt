package com.eightsines.worklifebalance.screen.goals

import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.state.LoadingState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsStore @Inject constructor() {
    var state = LoadingState.Initial
    val goals = mutableListOf<Goal>()

    fun reset() {
        state = LoadingState.Initial
    }
}

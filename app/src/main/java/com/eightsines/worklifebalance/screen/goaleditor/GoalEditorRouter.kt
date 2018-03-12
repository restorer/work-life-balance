package com.eightsines.worklifebalance.screen.goaleditor

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.core.state.LoadingState
import com.eightsines.worklifebalance.screen.goals.GoalsStore
import javax.inject.Inject

@ScopeScreen
class GoalEditorRouter @Inject constructor(private val activity : GoalEditorActivity,
        private val goalsStore : GoalsStore) {

    fun navigateBack() {
        goalsStore.state = LoadingState.Initial
        activity.finish()
    }
}

package com.eightsines.worklifebalance.screen.goals

import android.content.Intent
import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.screen.goaleditor.GoalEditorActivity
import com.eightsines.worklifebalance.screen.goaleditor.GoalEditorState
import com.eightsines.worklifebalance.screen.goaleditor.GoalEditorStore
import javax.inject.Inject

@ScopeScreen
class GoalsRouter @Inject constructor(private val activity : GoalsActivity,
        private val goalEditorStore : GoalEditorStore) {

    fun navigateToGoalEditor(goal : Goal?) {
        goalEditorStore.state = GoalEditorState.Initial
        goalEditorStore.goal = goal

        activity.startActivity(Intent(activity, GoalEditorActivity::class.java))
    }
}

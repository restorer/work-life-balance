package com.eightsines.worklifebalance.screen.goals

import com.eightsines.worklifebalance.core.app.BaseInteractor
import com.eightsines.worklifebalance.core.di.qualifiers.IsBackgroundExecutor
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiExecutor
import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.manager.PreferencesManager
import com.eightsines.worklifebalance.core.manager.TimeManager
import com.eightsines.worklifebalance.core.state.LoadingState
import com.eightsines.worklifebalance.core.util.ext.TaskExt
import com.eightsines.worklifebalance.core.util.ext.continueWithOn
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.Executor
import javax.inject.Inject

@ScopeScreen
class GoalsInteractor @Inject constructor(
        private val store : GoalsStore,
        private val preferencesManager : PreferencesManager,
        @IsBackgroundExecutor private val backgroundExecutor : Executor,
        @IsUiExecutor private val uiExecutor : Executor,
        private val timeManager : TimeManager,
        eventBus : EventBus) : BaseInteractor(eventBus) {

    fun loadGoals() {
        store.state = LoadingState.Loading
        store.goals.clear()

        TaskExt
                .callOn(backgroundExecutor) {
                    if (preferencesManager.hasGoals()) {
                        preferencesManager.getGoals()
                    } else {
                        listOf(Goal(1, 1, "Life"))
                    }
                }
                .continueWithOn(uiExecutor) { task ->
                    store.goals.addAll(task.result)
                    store.goals.sortBy { it.position }
                    store.state = LoadingState.Loaded

                    dispatchEvent(GoalsUpdatedEvent)
                }
    }

    fun setActiveGoal(activeGoal : Goal) {
        val currentTimeMillis = timeManager.getCurrentTimeMillis()
        var changed = false

        for (goal in store.goals) {
            if ((goal !== activeGoal && goal.isActive) || (goal === activeGoal && !goal.isActive)) {
                goal.credits = goal.getCurrentCredits(currentTimeMillis)
                goal.isActive = (goal == activeGoal)
                goal.lastActionTime = currentTimeMillis
                changed = true
            }
        }

        if (changed) {
            dispatchEvent(GoalsUpdatedEvent)

            TaskExt.callOn(backgroundExecutor) {
                preferencesManager.putGoals(store.goals)
            }
        }
    }
}

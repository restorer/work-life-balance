package com.eightsines.worklifebalance.screen.goaleditor

import com.eightsines.worklifebalance.core.app.BaseInteractor
import com.eightsines.worklifebalance.core.di.qualifiers.IsBackgroundExecutor
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiExecutor
import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.manager.PreferencesManager
import com.eightsines.worklifebalance.core.util.ext.TaskExt
import com.eightsines.worklifebalance.core.util.ext.continueWithOn
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.Executor
import javax.inject.Inject

@ScopeScreen
class GoalEditorInteractor @Inject constructor(
        private val store : GoalEditorStore,
        private val preferencesManager : PreferencesManager,
        @IsBackgroundExecutor private val backgroundExecutor : Executor,
        @IsUiExecutor private val uiExecutor : Executor,
        eventBus : EventBus) : BaseInteractor(eventBus) {

    fun initializeValues() {
        val goal = store.goal

        if (goal == null) {
            store.name = ""
            store.desiredTime = Triple("", "", "")
            store.totalTime = Triple("", "", "")
        } else {
            store.name = goal.name

            store.desiredTime = Triple(
                    if (goal.desiredTimeList.size > 2) goal.desiredTimeList[2].toString() else "",
                    if (goal.desiredTimeList.size > 1) goal.desiredTimeList[1].toString() else "",
                    if (goal.desiredTimeList.isNotEmpty()) goal.desiredTimeList[0].toString() else "")

            store.totalTime = Triple(
                    if (goal.totalTimeList.size > 2) goal.totalTimeList[2].toString() else "",
                    if (goal.totalTimeList.size > 1) goal.totalTimeList[1].toString() else "",
                    if (goal.totalTimeList.isNotEmpty()) goal.totalTimeList[0].toString() else "")
        }

        store.state = GoalEditorState.Loaded
        dispatchEvent(GoalEditorUpdatedEvent)
    }

    fun saveGoal() {
        store.state = GoalEditorState.Storing

        val goalId = store.goal?.id ?: 0
        val name = store.name
        val desiredTimeList = computeTimeList(store.desiredTime)
        val totalTimeList = computeTimeList(store.totalTime)

        TaskExt
                .callOn(backgroundExecutor) {
                    val goals = if (preferencesManager.hasGoals()) {
                        preferencesManager.getGoals().toMutableList()
                    } else {
                        mutableListOf()
                    }

                    val existingGoal = if (goalId > 0) goals.find { it.id == goalId } else null

                    if (existingGoal != null) {
                        existingGoal.name = name
                        existingGoal.desiredTimeList = desiredTimeList
                        existingGoal.totalTimeList = totalTimeList
                    } else {
                        goals.add(Goal(
                                id = (goals.maxBy { it.id }?.id ?: 0) + 1,
                                position = (goals.maxBy { it.position }?.position ?: 0) + 1,
                                name = name,
                                desiredTimeList = desiredTimeList,
                                totalTimeList = totalTimeList))
                    }

                    preferencesManager.putGoals(goals)
                }
                .continueWithOn(uiExecutor) { _ ->
                    store.state = GoalEditorState.Stored
                    dispatchEvent(GoalEditorUpdatedEvent)
                }
    }

    fun clearGoal() {
        store.state = GoalEditorState.Storing
        val goalId = store.goal?.id ?: 0

        TaskExt
                .callOn(backgroundExecutor) {
                    val goals = if (preferencesManager.hasGoals()) {
                        preferencesManager.getGoals().toMutableList()
                    } else {
                        mutableListOf()
                    }

                    val existingGoal = if (goalId > 0) goals.find { it.id == goalId } else null

                    if (existingGoal != null) {
                        existingGoal.credits = 0L
                        existingGoal.lastActionTime = 0L

                        preferencesManager.putGoals(goals)
                    }
                }
                .continueWithOn(uiExecutor) { _ ->
                    store.state = GoalEditorState.Stored
                    dispatchEvent(GoalEditorUpdatedEvent)
                }
    }

    fun deleteGoal() {
        store.state = GoalEditorState.Storing
        val goalId = store.goal?.id ?: 0

        TaskExt
                .callOn(backgroundExecutor) {
                    val goals = if (preferencesManager.hasGoals()) {
                        preferencesManager.getGoals().toMutableList()
                    } else {
                        mutableListOf()
                    }

                    val existingGoal = if (goalId > 0) goals.find { it.id == goalId } else null

                    if (existingGoal != null) {
                        goals.remove(existingGoal)

                        goals.sortBy { it.position }
                        goals.forEachIndexed { index, goal -> goal.position = index + 1 }

                        if (existingGoal.isActive) {
                            goals.findLast { it.desiredTime <= 0 || it.totalTime <= 0 }?.isActive = true
                        }

                        preferencesManager.putGoals(goals)
                    }
                }
                .continueWithOn(uiExecutor) { _ ->
                    store.state = GoalEditorState.Stored
                    dispatchEvent(GoalEditorUpdatedEvent)
                }
    }

    private fun computeTimeList(time : Triple<String, String, String>) : List<Int> {
        val time1Str = time.first.trim()
        val time2Str = time.second.trim()
        val time3Str = time.third.trim()

        val time1 = if (time1Str.isEmpty()) 0 else try {
            Integer.parseInt(time1Str)
        } catch (e : NumberFormatException) {
            0
        }

        val time2 = if (time2Str.isEmpty()) 0 else try {
            Integer.parseInt(time2Str)
        } catch (e : NumberFormatException) {
            0
        }

        val time3 = if (time3Str.isEmpty()) 0 else try {
            Integer.parseInt(time3Str)
        } catch (e : NumberFormatException) {
            0
        }

        val result = mutableListOf<Int>()

        if (time1 > 0) {
            result.add(time1)
        }

        if (time2 > 0) {
            result.add(time2)
        }

        if (time3 > 0) {
            result.add(time3)
        }

        return result
    }
}

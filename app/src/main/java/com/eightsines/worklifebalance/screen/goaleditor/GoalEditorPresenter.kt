package com.eightsines.worklifebalance.screen.goaleditor

import com.eightsines.worklifebalance.core.app.BasePresenter
import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@ScopeScreen
class GoalEditorPresenter @Inject constructor(
        private val viewComponent : GoalEditorActivity,
        private val store : GoalEditorStore,
        private val interactor : GoalEditorInteractor,
        private val router : GoalEditorRouter,
        eventBus : EventBus) : BasePresenter(eventBus) {

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGoalEditorUpdatedEvent(@Suppress("UNUSED_PARAMETER") event : GoalEditorUpdatedEvent) {
        update()
    }

    override fun update() {
        when (store.state) {
            GoalEditorState.Initial -> {
                interactor.initializeValues()
            }

            GoalEditorState.Loaded -> {
                val goal = store.goal

                viewComponent.setMenuItemVisibility(goal != null)
                viewComponent.setGoalName(store.name)
                viewComponent.setDesiredTime(store.desiredTime)
                viewComponent.setTotalTime(store.totalTime)
            }

            GoalEditorState.Storing -> Unit

            GoalEditorState.Stored -> {
                router.navigateBack()
            }
        }
    }

    fun onSaveClicked() {
        store.name = viewComponent.getGoalName()
        store.desiredTime = viewComponent.getDesiredTime()
        store.totalTime = viewComponent.getTotalTime()

        interactor.saveGoal()
    }

    fun onClearClicked() {
        viewComponent.showClearConfirm()
    }

    fun onClearConfirmed() {
        interactor.clearGoal()
    }

    fun onDeleteClicked() {
        viewComponent.showDeleteConfirm()
    }

    fun onDeleteConfirmed() {
        interactor.deleteGoal()
    }

    fun onBackPressed() {
        router.navigateBack()
    }
}

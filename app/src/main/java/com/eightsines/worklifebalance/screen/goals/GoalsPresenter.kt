package com.eightsines.worklifebalance.screen.goals

import android.os.Handler
import com.eightsines.worklifebalance.core.app.BasePresenter
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiHandler
import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.manager.TimeManager
import com.eightsines.worklifebalance.core.state.LoadingState
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@ScopeScreen
class GoalsPresenter @Inject constructor(
        private val viewComponent : GoalsActivity,
        private val store : GoalsStore,
        private val interactor : GoalsInteractor,
        private val router : GoalsRouter,
        @IsUiHandler private val uiHandler : Handler,
        eventBus : EventBus) : BasePresenter(eventBus) {

    companion object {
        private const val UPDATE_DELAY = TimeManager.SECOND_MILLIS
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGoalsUpdatedEvent(@Suppress("UNUSED_PARAMETER") event : GoalsUpdatedEvent) {
        update()
    }

    private val updateRunnable = Runnable {
        if (store.state == LoadingState.Loaded) {
            update()
        }
    }

    override fun update() {
        uiHandler.removeCallbacks(updateRunnable)

        when (store.state) {
            LoadingState.Initial -> {
                interactor.loadGoals()
            }

            LoadingState.Loading -> Unit

            LoadingState.Loaded -> {
                viewComponent.showGoals(store.goals)
                uiHandler.postDelayed(updateRunnable, UPDATE_DELAY)
            }

            LoadingState.LoadFailed -> Unit
        }
    }

    override fun onStop() {
        uiHandler.removeCallbacks(updateRunnable)
        super.onStop()
    }

    fun onItemClicked(goal : Goal) {
        interactor.setActiveGoal(goal)
    }

    fun onItemLongClicked(goal : Goal) {
        router.navigateToGoalEditor(goal)
    }

    fun onAddClicked() {
        router.navigateToGoalEditor(null)
    }
}

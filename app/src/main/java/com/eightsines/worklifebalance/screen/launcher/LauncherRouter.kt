package com.eightsines.worklifebalance.screen.launcher

import android.content.Intent
import com.eightsines.worklifebalance.screen.goals.GoalsActivity
import com.eightsines.worklifebalance.screen.goals.GoalsStore
import javax.inject.Inject

class LauncherRouter @Inject constructor(
        private val activity : LauncherActivity,
        private val goalsStore : GoalsStore) {

    fun navigateToGoals() {
        goalsStore.reset()
        activity.startActivity(Intent(activity, GoalsActivity::class.java))
        activity.finish()
    }

    fun closeApplication() {
        activity.finish()
    }
}

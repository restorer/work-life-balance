package com.eightsines.worklifebalance.screen.launcher

import com.eightsines.worklifebalance.core.app.BasePresenter
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class LauncherPresenter @Inject constructor(
        private val viewComponent : LauncherActivity,
        private val router : LauncherRouter,
        eventBus : EventBus) : BasePresenter(eventBus) {

    override fun update() {
        try {
            // Убедится в том, что всё в порядке с internal storage.
            // Смех смехом, но иногда на некоторых девайсах приложение криво устанавливается,
            // и могут быть проблемы.
            viewComponent.filesDir.canonicalPath
        } catch (e : Exception) {
            viewComponent.onInternalStorageFailed()
            return
        }

        router.navigateToGoals()
    }

    fun onCloseApplication() {
        router.closeApplication()
    }
}

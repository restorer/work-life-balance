package com.eightsines.worklifebalance.core.app

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException

abstract class BasePresenter(private val eventBus : EventBus) : LifecycleObserver {
    private var isEventBusRegistered : Boolean = false

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        try {
            eventBus.register(this)
            isEventBusRegistered = true
        } catch (e : EventBusException) {
            // ignored
            // EventBus throws exception when and its super classes have no public methods with the @Subscribe annotation
        }

        update()
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        if (isEventBusRegistered) {
            isEventBusRegistered = false
            eventBus.unregister(this)
        }
    }

    abstract fun update()
}

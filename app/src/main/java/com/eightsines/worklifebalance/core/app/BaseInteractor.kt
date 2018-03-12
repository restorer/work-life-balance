package com.eightsines.worklifebalance.core.app

import org.greenrobot.eventbus.EventBus

abstract class BaseInteractor(private val eventBus : EventBus) {
    fun dispatchEvent(event : Any) {
        eventBus.post(event)
    }
}

package com.eightsines.worklifebalance.screen.goals.di

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.screen.goals.GoalsActivity
import dagger.Subcomponent

@ScopeScreen
@Subcomponent(modules = [GoalsModule::class])
interface GoalsSubcomponent {
    fun inject(activity : GoalsActivity)
}

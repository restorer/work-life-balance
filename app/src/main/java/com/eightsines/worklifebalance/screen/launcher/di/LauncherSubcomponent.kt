package com.eightsines.worklifebalance.screen.launcher.di

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.screen.launcher.LauncherActivity
import dagger.Subcomponent

@ScopeScreen
@Subcomponent(modules = [LauncherModule::class])
interface LauncherSubcomponent {
    fun inject(activity : LauncherActivity)
}

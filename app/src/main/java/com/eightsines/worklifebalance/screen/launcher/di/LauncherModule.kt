package com.eightsines.worklifebalance.screen.launcher.di

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.screen.launcher.LauncherActivity
import dagger.Module
import dagger.Provides

@Module
class LauncherModule(private val activity : LauncherActivity) {
    @Provides
    @ScopeScreen
    fun provideActivity() = activity
}

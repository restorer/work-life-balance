package com.eightsines.worklifebalance.screen.goals.di

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.screen.goals.GoalsActivity
import dagger.Module
import dagger.Provides

@Module
class GoalsModule(private val activity : GoalsActivity) {
    @Provides
    @ScopeScreen
    fun provideActivity() = activity
}


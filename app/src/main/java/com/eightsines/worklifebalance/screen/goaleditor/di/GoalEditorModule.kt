package com.eightsines.worklifebalance.screen.goaleditor.di

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.screen.goaleditor.GoalEditorActivity
import dagger.Module
import dagger.Provides

@Module
class GoalEditorModule(private val activity : GoalEditorActivity) {
    @Provides
    @ScopeScreen
    fun provideActivity() = activity
}

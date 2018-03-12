package com.eightsines.worklifebalance.screen.goaleditor.di

import com.eightsines.worklifebalance.core.di.scopes.ScopeScreen
import com.eightsines.worklifebalance.screen.goaleditor.GoalEditorActivity
import dagger.Subcomponent

@ScopeScreen
@Subcomponent(modules = [GoalEditorModule::class])
interface GoalEditorSubcomponent {
    fun inject(activity : GoalEditorActivity)
}

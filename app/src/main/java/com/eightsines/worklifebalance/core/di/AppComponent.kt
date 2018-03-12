package com.eightsines.worklifebalance.core.di

import com.eightsines.worklifebalance.screen.goaleditor.di.GoalEditorModule
import com.eightsines.worklifebalance.screen.goaleditor.di.GoalEditorSubcomponent
import com.eightsines.worklifebalance.screen.goals.di.GoalsModule
import com.eightsines.worklifebalance.screen.goals.di.GoalsSubcomponent
import com.eightsines.worklifebalance.screen.launcher.di.LauncherModule
import com.eightsines.worklifebalance.screen.launcher.di.LauncherSubcomponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun plus(module : LauncherModule) : LauncherSubcomponent
    fun plus(module : GoalsModule) : GoalsSubcomponent
    fun plus(module : GoalEditorModule) : GoalEditorSubcomponent
}

package com.eightsines.worklifebalance.screen.goaleditor

import com.eightsines.worklifebalance.core.entity.Goal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalEditorStore @Inject constructor() {
    var state = GoalEditorState.Initial
    var goal : Goal? = null

    var name = ""
    var desiredTime = Triple("", "", "")
    var totalTime = Triple("", "", "")
}

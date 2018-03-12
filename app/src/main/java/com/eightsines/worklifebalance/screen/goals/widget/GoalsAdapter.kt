package com.eightsines.worklifebalance.screen.goals.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.manager.TimeManager

class GoalsAdapter(
        private val listener : GoalViewHolder.Listener,
        private val timeManager : TimeManager) : RecyclerView.Adapter<GoalViewHolder>() {

    private var goals : List<Goal>? = null

    init {
        setHasStableIds(true)
    }

    fun setGoals(goals : List<Goal>) {
        this.goals = goals
        notifyDataSetChanged()
    }

    override fun getItemCount() = goals?.size ?: 0
    override fun getItemId(position : Int) = position.toLong()

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : GoalViewHolder =
            GoalViewHolder.create(parent, listener, timeManager)

    override fun onBindViewHolder(holder : GoalViewHolder, position : Int) {
        goals?.get(position)?.let { holder.update(it) }
    }
}

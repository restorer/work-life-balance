package com.eightsines.worklifebalance.screen.goals.widget

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightsines.worklifebalance.R
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.manager.TimeManager
import kotlinx.android.synthetic.main.goals__item.view.*

class GoalViewHolder(
        itemView : View,
        private val listener : GoalViewHolder.Listener,
        private val timeManager : TimeManager) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(
                parent : ViewGroup,
                listener : GoalViewHolder.Listener,
                timeManager : TimeManager) : GoalViewHolder {

            return GoalViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.goals__item, parent, false),
                    listener,
                    timeManager)
        }
    }

    interface Listener {
        fun onItemClick(goal : Goal)
        fun onItemLongClick(goal : Goal)
    }

    private var currentGoal : Goal? = null

    init {
        itemView.setOnClickListener {
            currentGoal?.let { listener.onItemClick(it) }
        }

        itemView.setOnLongClickListener {
            currentGoal?.let { listener.onItemLongClick(it) }
            true
        }
    }

    fun update(goal : Goal) {
        currentGoal = goal

        itemView.backgroundView.isSelected = goal.isActive
        itemView.nameView.text = goal.name

        if (goal.desiredTime <= 0 || goal.totalTime <= 0) {
            itemView.creditsView.visibility = View.GONE
        } else {
            val credits = goal.getDisplayCredits(timeManager.getCurrentTimeMillis())

            itemView.creditsView.visibility = View.VISIBLE
            itemView.creditsView.text = formatCredits(credits)

            itemView.creditsView.setTypeface(
                    itemView.creditsView.typeface,
                    if (credits < 0) Typeface.BOLD else 0)
        }
    }

    private fun formatCredits(_credits : Long) : String {
        var credits = _credits

        if (credits == 0L) {
            return "-"
        }

        val isNegative : Boolean

        if (credits < 0) {
            isNegative = true
            credits = -credits
        } else {
            isNegative = false
        }

        val days = (credits / 86400L).toInt()
        val hours = ((credits / 3600L) % 24L).toInt()
        val minutes = ((credits / 60L) % 60L).toInt()
        val seconds = (credits % 60L).toInt()

        var result = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        if (days > 0) {
            result = "$days.$result"
        }

        if (isNegative) {
            result = "-$result"
        }

        return result
    }
}

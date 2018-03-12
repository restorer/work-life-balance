package com.eightsines.worklifebalance.screen.goals

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import com.eightsines.worklifebalance.R
import com.eightsines.worklifebalance.core.app.BaseActivity
import com.eightsines.worklifebalance.core.di.AppComponent
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiHandler
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.manager.TimeManager
import com.eightsines.worklifebalance.screen.goals.di.GoalsModule
import com.eightsines.worklifebalance.screen.goals.widget.GoalViewHolder
import com.eightsines.worklifebalance.screen.goals.widget.GoalsAdapter
import kotlinx.android.synthetic.main.goals__activity.*
import javax.inject.Inject

class GoalsActivity : BaseActivity<GoalsPresenter>() {
    @Inject override lateinit var presenter : GoalsPresenter
    @Inject lateinit var timeManager : TimeManager
    @field:[Inject IsUiHandler] lateinit var uiHandler : Handler

    private lateinit var adapter : GoalsAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goals__activity)

        adapter = GoalsAdapter(object : GoalViewHolder.Listener {
            override fun onItemClick(goal : Goal) {
                presenter.onItemClicked(goal)
            }

            override fun onItemLongClick(goal : Goal) {
                presenter.onItemLongClicked(goal)
            }
        }, timeManager)

        setSupportActionBar(toolbarView)

        itemsView.let {
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
    }

    override fun inject(component : AppComponent) = component.plus(GoalsModule(this)).inject(this)

    override fun onCreateOptionsMenu(menu : Menu) : Boolean {
        menuInflater.inflate(R.menu.goals, menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean = when (item.itemId) {
        R.id.addMenuItem -> {
            uiHandler.post { presenter.onAddClicked() }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    fun showGoals(goals : List<Goal>) {
        adapter.setGoals(goals)
    }
}

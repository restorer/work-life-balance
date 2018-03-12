package com.eightsines.worklifebalance.screen.goaleditor

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.eightsines.worklifebalance.R
import com.eightsines.worklifebalance.core.app.BaseActivity
import com.eightsines.worklifebalance.core.di.AppComponent
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiHandler
import com.eightsines.worklifebalance.screen.goaleditor.di.GoalEditorModule
import kotlinx.android.synthetic.main.goaleditor__activity.*
import javax.inject.Inject

class GoalEditorActivity : BaseActivity<GoalEditorPresenter>() {
    @Inject override lateinit var presenter : GoalEditorPresenter
    @field:[Inject IsUiHandler] lateinit var uiHandler : Handler

    private var isMenuItemsVisible = false

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goaleditor__activity)

        setSupportActionBar(toolbarView)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        saveBtnView.setOnClickListener { presenter.onSaveClicked() }
    }

    override fun inject(component : AppComponent) = component.plus(GoalEditorModule(this)).inject(this)

    override fun onCreateOptionsMenu(menu : Menu) : Boolean {
        menuInflater.inflate(R.menu.goaleditor, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu : Menu) : Boolean {
        menu.findItem(R.id.clearMenuItem).isVisible = isMenuItemsVisible
        menu.findItem(R.id.deleteMenuItem).isVisible = isMenuItemsVisible

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean = when (item.itemId) {
        R.id.clearMenuItem -> {
            uiHandler.post { presenter.onClearClicked() }
            true
        }

        R.id.deleteMenuItem -> {
            uiHandler.post { presenter.onDeleteClicked() }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    fun setMenuItemVisibility(visible : Boolean) {
        isMenuItemsVisible = visible
        invalidateOptionsMenu()
    }

    fun setGoalName(name : String) {
        goalNameView.setText(name)
    }

    fun getGoalName() = goalNameView.text.toString()

    fun setDesiredTime(desiredTime : Triple<String, String, String>) {
        desiredTime1View.setText(desiredTime.first)
        desiredTime2View.setText(desiredTime.second)
        desiredTime3View.setText(desiredTime.third)
    }

    fun getDesiredTime() = Triple(desiredTime1View.text.toString(),
            desiredTime2View.text.toString(),
            desiredTime3View.text.toString())

    fun setTotalTime(totalTime : Triple<String, String, String>) {
        totalTime1View.setText(totalTime.first)
        totalTime2View.setText(totalTime.second)
        totalTime3View.setText(totalTime.third)
    }

    fun getTotalTime() = Triple(totalTime1View.text.toString(),
            totalTime2View.text.toString(),
            totalTime3View.text.toString())

    fun showClearConfirm() {
        if (dialog != null) {
            return
        }

        dialog = AlertDialog.Builder(this)
                .setMessage(R.string.goaleditor__confirm_clear)
                .setPositiveButton(R.string.common__ok, { _, _ ->
                    dialog = null
                    presenter.onClearConfirmed()
                })
                .setNegativeButton(R.string.common__cancel, { _, _ ->
                    dialog = null
                })
                .setCancelable(false)
                .show()
    }

    fun showDeleteConfirm() {
        if (dialog != null) {
            return
        }

        dialog = AlertDialog.Builder(this)
                .setMessage(R.string.goaleditor__confirm_delete)
                .setPositiveButton(R.string.common__ok, { _, _ ->
                    dialog = null
                    presenter.onDeleteConfirmed()
                })
                .setNegativeButton(R.string.common__cancel, { _, _ ->
                    dialog = null
                })
                .setCancelable(false)
                .show()
    }
}

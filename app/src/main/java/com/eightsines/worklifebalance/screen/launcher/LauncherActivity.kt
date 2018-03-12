package com.eightsines.worklifebalance.screen.launcher

import android.support.v7.app.AlertDialog
import com.eightsines.worklifebalance.App
import com.eightsines.worklifebalance.R
import com.eightsines.worklifebalance.core.app.BaseActivity
import com.eightsines.worklifebalance.core.di.AppComponent
import com.eightsines.worklifebalance.screen.launcher.di.LauncherModule
import javax.inject.Inject

class LauncherActivity : BaseActivity<LauncherPresenter>() {
    @Inject override lateinit var presenter : LauncherPresenter

    override fun inject(component : AppComponent) = component.plus(LauncherModule(this)).inject(this)

    override fun ensureInitializedProperly() : Boolean {
        App.self.initializedProperly = true
        return true
    }

    fun onInternalStorageFailed() {
        if (dialog != null) {
            return
        }

        dialog = AlertDialog.Builder(this)
                .setMessage(R.string.launcher__internal_storage_error)
                .setPositiveButton(R.string.common__ok, { _, _ ->
                    dialog = null
                    presenter.onCloseApplication()
                })
                .setCancelable(false)
                .show()
    }
}

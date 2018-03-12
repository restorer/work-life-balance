package com.eightsines.worklifebalance.core.app

import android.app.Dialog
import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.eightsines.worklifebalance.App
import com.eightsines.worklifebalance.core.di.AppComponent

abstract class BaseActivity<TPresenter> : AppCompatActivity() where TPresenter : LifecycleObserver {
    abstract var presenter : TPresenter
    protected var dialog : Dialog? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        if (!ensureInitializedProperly()) {
            return
        }

        super.onCreate(savedInstanceState)
        inject(App.self.component)
        lifecycle.addObserver(presenter)
    }

    abstract fun inject(component : AppComponent)

    override fun onOptionsItemSelected(item : MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    protected open fun ensureInitializedProperly() : Boolean {
        if (App.self.initializedProperly) {
            return true
        }

        // startActivity(Intent(this, LauncherActivity::class.java)
        //     .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
        return false
    }

    override fun onDestroy() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }

        super.onDestroy()
    }
}


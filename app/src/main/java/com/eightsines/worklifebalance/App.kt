package com.eightsines.worklifebalance

import android.app.Application
import android.os.Handler
import com.eightsines.worklifebalance.core.di.AppComponent
import com.eightsines.worklifebalance.core.di.AppModule
import com.eightsines.worklifebalance.core.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary

class App : Application() {
    companion object {
        lateinit var self : App
    }

    lateinit var component : AppComponent
    var initializedProperly = false

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)

        self = this

        // component создаётся вручную (вместо by lazy), чтобы быть уверенным,
        // что Handler() создался именно в UI-потоке
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this, Handler()))
                .build()
    }
}

package com.eightsines.worklifebalance.core.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.preference.PreferenceManager
import bolts.Task
import com.eightsines.worklifebalance.AppConfig
import com.eightsines.worklifebalance.core.di.qualifiers.IsAppContext
import com.eightsines.worklifebalance.core.di.qualifiers.IsBackgroundExecutor
import com.eightsines.worklifebalance.core.di.qualifiers.IsDefaultSharedPreferences
import com.eightsines.worklifebalance.core.di.qualifiers.IsIsDebug
import com.eightsines.worklifebalance.core.di.qualifiers.IsLogTag
import com.eightsines.worklifebalance.core.di.qualifiers.IsNetworkExecutor
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiExecutor
import com.eightsines.worklifebalance.core.di.qualifiers.IsUiHandler
import com.eightsines.worklifebalance.core.util.ExecutorUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
class AppModule(private val context : Context, private val uiHandler : Handler) {
    @Provides
    @Singleton
    @IsAppContext
    fun provideAppContext() = context

    @Provides
    @Singleton
    @IsUiHandler
    fun provideUiHandler() = uiHandler

    @Provides
    @Singleton
    @IsDefaultSharedPreferences
    fun provideDefaultSharedPreferences() : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    @IsIsDebug
    fun provideIsDebug() = AppConfig.DEBUG

    @Provides
    @Singleton
    @IsLogTag
    fun provideLogTag() = AppConfig.LOG_TAG

    @Provides
    @Singleton
    fun provideEventBus() : EventBus = EventBus.getDefault()

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    @IsUiExecutor
    fun provideUiExecutor() : Executor = Task.UI_THREAD_EXECUTOR

    @Provides
    @Singleton
    @IsBackgroundExecutor
    fun provideBackgroundExecutor() : Executor = Task.BACKGROUND_EXECUTOR

    @Provides
    @Singleton
    @IsNetworkExecutor
    fun provideNetworkExecutor() : Executor = ExecutorUtils.newFixedThreadPool(64)
}

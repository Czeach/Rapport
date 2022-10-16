package com.czech.rapport.di

import android.content.Context
import android.content.SharedPreferences
import com.czech.rapport.BaseApplication
import com.czech.rapport.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AppModule {

    @[Provides Singleton]
    fun provideBaseApplication(
        @ApplicationContext app: Context
    ): BaseApplication {
        return app as BaseApplication
    }

    @[Provides Singleton]
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(R.string.app_name.toString() + "_prefs", Context.MODE_PRIVATE)
    }
}
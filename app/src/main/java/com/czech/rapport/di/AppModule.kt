package com.czech.rapport.di

import android.content.Context
import com.czech.rapport.BaseApplication
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
}
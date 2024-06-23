package com.example.cheesechase.di

import android.app.Application
import com.example.cheesechase.gyroscope.Sample
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun giveSample(context: Application): Sample {
        return Sample(context)
    }
}
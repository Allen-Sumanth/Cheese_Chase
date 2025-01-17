package com.example.cheesechase.di

import android.app.Application
import com.example.cheesechase.data.DataStorage
import com.example.cheesechase.gyroscope.GyroSensor
import com.example.cheesechase.gyroscope.MeasurableSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGyroSensor(app: Application): MeasurableSensor {
        return GyroSensor(app)
    }

    @Provides
    @Singleton
    fun provideDataStore(app: Application): DataStorage {
        return DataStorage(app)
    }
}
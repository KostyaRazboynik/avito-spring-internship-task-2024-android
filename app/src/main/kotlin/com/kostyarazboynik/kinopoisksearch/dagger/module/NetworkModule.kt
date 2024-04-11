package com.kostyarazboynik.kinopoisksearch.dagger.module

import android.content.Context
import com.kostyarazboynik.domain.connection.ConnectivityObserver
import com.kostyarazboynik.domain.connection.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides

@Module
object NetworkModule {

    @Provides
    fun provideConnectivityObserver(context: Context): ConnectivityObserver =
        NetworkConnectivityObserver(context)
}
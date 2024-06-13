package com.example.nexttogo

import android.app.Application
import com.example.nexttogo.data.AppContainer
import com.example.nexttogo.data.DefaultAppContainer

class NextToGoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
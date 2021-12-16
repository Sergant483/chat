package com.senla.chat

import android.app.Application
import com.senla.chat.di.ApplicationComponent
import com.senla.chat.di.DaggerApplicationComponent

class App : Application() {
    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        appComponent = DaggerApplicationComponent.create()
    }
}
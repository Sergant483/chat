package com.senla.chat

import android.app.Application
import com.senla.chat.di.ApplicationComponent
import com.senla.chat.di.DaggerApplicationComponent

class App : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        appComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }
}

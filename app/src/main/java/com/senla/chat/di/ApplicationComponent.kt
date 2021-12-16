package com.senla.chat.di

import dagger.Component
import dagger.Module


@Component(modules = [ApplicationComponent.AppModule::class])
interface ApplicationComponent {

    @Module
    class AppModule {


    }
}

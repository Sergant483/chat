package com.senla.chat.di

import com.senla.chat.presentation.fragments.terms.TermsFragment
import com.senla.chat.presentation.fragments.terms.TermsViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [ApplicationComponent.AppModule::class])
interface ApplicationComponent {

    fun inject(termsFragment: TermsFragment)

    @Module
    class AppModule {
        @Provides
        fun provideTermsViewModelFactory(): TermsViewModelFactory = TermsViewModelFactory()
    }
}

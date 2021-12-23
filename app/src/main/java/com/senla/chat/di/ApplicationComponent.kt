package com.senla.chat.di

import com.senla.chat.presentation.fragments.chat.ChatFragment
import com.senla.chat.presentation.fragments.chat.ChatViewModelFactory
import com.senla.chat.presentation.fragments.terms.TermsFragment
import com.senla.chat.presentation.fragments.terms.TermsViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides


@Component(modules = [ApplicationComponent.AppModule::class])
interface ApplicationComponent {
    fun inject(termsFragment: TermsFragment)
    fun inject(chatFragment: ChatFragment)

    @Module
    class AppModule {
        @Provides
        fun provideTermsViewModelFactory(): TermsViewModelFactory = TermsViewModelFactory()

        @Provides
        fun provideChatViewModelFactory(): ChatViewModelFactory = ChatViewModelFactory()
    }
}

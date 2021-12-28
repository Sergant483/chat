package com.senla.chat.di.chat

import com.senla.chat.di.terms.TermsModule
import com.senla.chat.presentation.fragments.chat.ChatFragment
import dagger.Subcomponent

@Subcomponent(modules = [ChatModule::class])
interface ChatComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChatComponent
    }

    fun inject(fragment: ChatFragment)
}
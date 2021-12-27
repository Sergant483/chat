package com.senla.chat.di.chat

import androidx.lifecycle.ViewModel
import com.senla.chat.di.ViewModelKey
import com.senla.chat.presentation.fragments.chat.ChatViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TermsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    fun bindViewModel(viewmodel: ChatViewModel): ViewModel
}
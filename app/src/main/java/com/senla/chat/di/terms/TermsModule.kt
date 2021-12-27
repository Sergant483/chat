package com.senla.chat.di.terms

import androidx.lifecycle.ViewModel
import com.senla.chat.di.ViewModelKey
import com.senla.chat.presentation.fragments.terms.TermsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TermsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel::class)
    fun bindViewModel(viewmodel: TermsViewModel): ViewModel
}

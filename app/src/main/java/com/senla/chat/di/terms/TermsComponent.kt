package com.senla.chat.di.terms

import com.senla.chat.presentation.fragments.terms.TermsFragment
import dagger.Subcomponent

@Subcomponent(modules = [TermsModule::class])
interface TermsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TermsComponent
    }

    fun inject(fragment: TermsFragment)
}

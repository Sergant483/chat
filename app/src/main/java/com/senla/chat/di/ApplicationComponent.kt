package com.senla.chat.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.senla.chat.di.chat.ChatComponent
import com.senla.chat.di.terms.TermsComponent
import com.senla.chat.presentation.MainActivity
import com.senla.chat.presentation.fragments.utils.PreferenceManager
import com.senla.chat.service.CloseService
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Component(
    modules = [
        ApplicationComponent.AppModule::class,
        ViewModelBuilderModule::class,
        SubcomponentsModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun termsComponent(): TermsComponent.Factory
    fun chatComponent(): ChatComponent.Factory
    fun inject(service: CloseService)

    @Module
    class AppModule {

        @Provides
        fun provideFirebase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    }
}

@Module(
    subcomponents = [
        TermsComponent::class,
        ChatComponent::class
    ]
)
object SubcomponentsModule
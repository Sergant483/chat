package com.senla.chat.di

import android.content.Context
import com.senla.chat.di.chat.ChatComponent
import com.senla.chat.di.terms.TermsComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module


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

    @Module
    class AppModule {}
}

@Module(
    subcomponents = [
        TermsComponent::class,
        ChatComponent::class
    ]
)
object SubcomponentsModule
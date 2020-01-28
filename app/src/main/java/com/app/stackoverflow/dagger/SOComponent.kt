package com.app.stackoverflow.dagger

import android.app.Application
import com.app.stackoverflow.SOApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [SOModule::class,
    SOActivityModule::class,
    AndroidSupportInjectionModule::class])
@Singleton
interface SOComponent {

    /*
     * We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     *
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): SOComponent
    }

    /*
     * This is our custom Application class
     * */
    fun inject(soApplication: SOApplication)
}
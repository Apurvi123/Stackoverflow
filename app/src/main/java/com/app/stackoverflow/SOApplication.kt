package com.app.stackoverflow

import android.app.Activity
import android.app.Application
import com.app.stackoverflow.dagger.DaggerSOComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class SOApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerSOComponent.builder().
            application(this).
            build().
            inject(this)
    }
}
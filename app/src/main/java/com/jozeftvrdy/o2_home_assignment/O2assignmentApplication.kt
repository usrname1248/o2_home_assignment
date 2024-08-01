package com.jozeftvrdy.o2_home_assignment

import android.app.Application
import com.jozeftvrdy.o2_home_assignment.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class O2assignmentApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@O2assignmentApplication)
            modules(allModules)
        }
    }
}